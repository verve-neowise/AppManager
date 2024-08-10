package com.axtech.appmanager.domain.repositories

import com.axtech.appmanager.data.dto.AppResponse
import com.axtech.appmanager.domain.models.App
import com.axtech.appmanager.util.NetworkFlow
import com.axtech.downloader.DownloadState
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface IAppsRepository {
    fun getAllApps(): NetworkFlow<List<App>>
    fun downloadApp(id: Int, fullPath: String, filename: String): Flow<DownloadState>
    fun cancelDownload(id: Int)
}