package com.axtech.appmanager.util


import android.system.SystemCleaner
import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.io.OutputStream


sealed class NetworkResult<T> {
    class Pending<T>(val progress: Float = 0f) : NetworkResult<T>()
    class Error<T>(val message: String) : NetworkResult<T>()
    class Success<T>(val data: T) : NetworkResult<T>()
}

typealias NetworkFlow<T> = Flow<NetworkResult<T>>

open class BaseResponse(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: String? = null
)

fun <T, R> Flow<T>.collectFlow(transform: (T) -> R): NetworkFlow<R> {
    return flow {
        emit(NetworkResult.Pending())

        flowOn(Dispatchers.IO)
            .catch { e ->
                if (e is HttpException) {
                    val body = e.response()?.errorBody()?.string()
                    val message = body ?: e.message()

                    Log.e("Flow", message)

                    emit(NetworkResult.Error(message))
                } else {
                    Log.e("Flow", e.message!!)
                    emit(NetworkResult.Error(e.message!!))
                }
            }
            .collect {
                if (it is BaseResponse && !it.success) {
                    Log.e("Error", it.message ?: "error")
                    emit(NetworkResult.Error(it.message ?: "error"))
                } else {
                    emit(
                        NetworkResult.Success(
                            transform(it)
                        )
                    )
                }
            }
    }
}

fun ResponseBody.saveFile(destinationFile: File, onProgress: (readBytes: Long, isDone: Boolean) -> Unit) {
    byteStream().use { inputStream ->

        destinationFile.outputStream().use { outputStream ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var progressBytes = 0L
            var bytes = inputStream.read(buffer)
            while (bytes >= 0) {
                outputStream.write(buffer, 0, bytes)
                progressBytes += bytes
                bytes = inputStream.read(buffer)
                onProgress(progressBytes, false)
            }
            onProgress(progressBytes, true)
        }
    }
}


sealed class DownloadState {
    object Starting : DownloadState()
    data class Downloading(val progress: Float) : DownloadState()
    object Finished : DownloadState()
    data class Failed(val error: Throwable? = null) : DownloadState()
}


fun downloadWithProgress(url: String, destinationFile: File) = callbackFlow {
    val request: Request = Request.Builder()
        .url(url)
        .build()

    val client: OkHttpClient = OkHttpClient.Builder()
        .build()

    val call = client.newCall(request)
    val response = call.execute()
    val totalBytes = response.body?.contentLength() ?: -1

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
                    trySend(NetworkResult.Pending(progressBytes.toFloat() / totalBytes.toFloat()))
                    estimateTime = System.currentTimeMillis()
                }
            }

            if (!isActive) {
                Log.d("Coroutine canceled", "download canceled at progress=${progressBytes.toFloat() / totalBytes.toFloat()}")
                trySend(NetworkResult.Error("Canceled"))
            }

            inputStream.close()
            outputStream.close()
            trySend(NetworkResult.Success(Unit))
        }
    }

    awaitClose {
        Log.d("downloadWithProgress", "await close")
        if (!call.isCanceled()) {
            call.cancel()
        }
    }
}.cancellable()