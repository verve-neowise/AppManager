package com.axtech.downloader

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import java.io.File

class Downloader private constructor() {

    private var service: DownloadService? = null

    private val serviceConnection = object : ServiceConnection {
         override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            val downloadBinder = binder as DownloadService.DownloadBinder
            service = downloadBinder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }
    }

    fun download(url: String, destinationFile: File): Download {
        val service = this.service ?: throw IllegalStateException("Service is not running")
        return service.download(url, destinationFile)
    }

    fun isDownloading(url: String): Boolean {
        val service = this.service ?: throw IllegalStateException("Service is not running")
        return service.isDownloading(url)
    }

    fun getDownloadByUrl(url: String): Download? {
        val service = this.service ?: throw IllegalStateException("Service is not running")
        return service.getDownloadByUrl(url)
    }

    fun getDownloadById(id: String): Download? {
        val service = this.service ?: throw IllegalStateException("Service is not running")
        return service.getDownloadById(id)
    }

    fun cancel(id: String) {
        val service = this.service ?: throw IllegalStateException("Service is not running")
        service.cancel(id)
    }

    private fun connect(context: Context) {
        if (service != null) {
            return
        }
        Intent(context, DownloadService::class.java).also { intent ->
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            }
        }
    }

    companion object {

        private var instance: Downloader = Downloader()

        fun getInstance(context: Context): Downloader {
            instance.connect(context)
            return instance
        }
    }
}