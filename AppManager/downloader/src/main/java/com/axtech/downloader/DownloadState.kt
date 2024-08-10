package com.axtech.downloader

sealed interface DownloadState {

    data class Completed(val path: String): DownloadState
    data class Loading(val progress: Float): DownloadState
    data class Failure(val exception: Exception): DownloadState

    companion object {
        fun completed(path: String) = Completed(path)
        fun progress(progress: Float) = Loading(progress)
        fun failure(exception: Exception) = Failure(exception)
    }
}