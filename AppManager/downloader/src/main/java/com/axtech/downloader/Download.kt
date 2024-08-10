package com.axtech.downloader

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import java.io.File

class Download(
    val id: String,
    val url: String,
    val destinationFile: File,
    val progress: Flow<DownloadState>,
    val metadata: HashMap<String, String>,
    internal val downloadJob: Job
) {

    fun isActive(): Boolean {
        return downloadJob.isActive
    }

    fun cancel() {
        if (downloadJob.isActive) {
            downloadJob.cancel()
            destinationFile.delete()
        }
    }
}