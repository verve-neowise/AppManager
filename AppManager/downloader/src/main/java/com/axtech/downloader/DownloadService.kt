package com.axtech.downloader

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.UUID

internal class DownloadService : Service() {

    private val downloadMap = HashMap<String, Download>()

    private val downloadJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + downloadJob)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.download_notification_id),
                "Download app in process",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(333, buildNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
        else {
            startForeground(333, buildNotification())
        }

        return START_STICKY
    }


    private fun buildNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, getString(R.string.download_notification_id))
                .setContentTitle("App Manager")
                .setContentText("Download progress")
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle("App Manager")
                .setContentText("Download progress")
                .build()
        }
    }

    override fun onDestroy() {
        downloadJob.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        return DownloadBinder()
    }

    fun cancel(id: String) {
        val download = downloadMap.values.find { it.id == id }
        if (download != null) {
            download.cancel()
            download.downloadJob.cancel()
            download.destinationFile.delete()
            downloadMap.remove(download.url)
        }
    }

    fun download(
        url: String,
        destinationFile: File,
        metadata: HashMap<String, String> = hashMapOf()
    ): Download {

        val existsDownload = downloadMap[url]

        if (existsDownload != null) {
            return existsDownload
        }

        val progress = MutableSharedFlow<DownloadState>(
            replay = 1,
            extraBufferCapacity = 1,
            BufferOverflow.DROP_OLDEST
        )

        val job = scope.launch {
            downloadFlow(url, destinationFile).collect {
                progress.tryEmit(it)
            }
        }

        val newDownload = Download(
            id = UUID.randomUUID().toString(),
            url = url,
            destinationFile = destinationFile,
            progress = progress,
            metadata = metadata,
            downloadJob = job
        )

        downloadMap[url] = newDownload

        return newDownload
    }

    fun getDownloadById(id: String): Download? {
        for ((_, download) in downloadMap) {
            if (download.id == id) {
                return download
            }
        }
        return null
    }

    fun getDownloadByUrl(url: String): Download? {
        for ((_, download) in downloadMap) {
            if (download.url == url) {
                return download
            }
        }
        return null
    }

    fun isDownloading(url: String): Boolean {
        val existsDownload = downloadMap[url]
        return existsDownload != null
    }

    private fun downloadFlow(url: String, destinationFile: File) = callbackFlow {
        val request: Request = Request.Builder()
            .url(url)
            .build()

        val client: OkHttpClient = OkHttpClient.Builder()
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val totalBytes = response.body?.contentLength() ?: -1

        if (destinationFile.exists() && destinationFile.length() == totalBytes) {
            trySend(DownloadState.completed(destinationFile.absolutePath))
        }
        else {
            if (destinationFile.exists()) {
                destinationFile.delete()
                destinationFile.createNewFile()
            }
            response.body?.byteStream()?.use { inputStream ->
                destinationFile.outputStream().use { outputStream ->
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var progressBytes = 0L
                    var bytes = inputStream.read(buffer)
                    var estimateTime: Long = System.currentTimeMillis()

                    while (bytes >= 0 && isActive) {
                        outputStream.write(buffer, 0, bytes)
                        progressBytes += bytes
                        bytes = inputStream.read(buffer)

                        if (System.currentTimeMillis() - estimateTime > 1000L) {
                            trySend(DownloadState.progress(progressBytes.toFloat() / totalBytes.toFloat()))
                            estimateTime = System.currentTimeMillis()
                        }
                    }

                    if (!isActive) {
                        trySend(DownloadState.failure(IllegalStateException("Canceled")))
                    }

                    inputStream.close()
                    outputStream.close()
                }
            }
            trySend(DownloadState.completed(destinationFile.absolutePath))
        }

        awaitClose {
            if (!call.isCanceled()) {
                call.cancel()
            }
        }
    }.cancellable()
        .flowOn(Dispatchers.IO)

    inner class DownloadBinder : Binder() {
        fun getService(): DownloadService = this@DownloadService
    }

}