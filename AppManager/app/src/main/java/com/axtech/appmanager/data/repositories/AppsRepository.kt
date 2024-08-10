package com.axtech.appmanager.data.repositories

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.axtech.appmanager.data.client.AppsService
import com.axtech.appmanager.data.dto.AppDto
import com.axtech.appmanager.data.mappers.map
import com.axtech.appmanager.domain.models.App
import com.axtech.appmanager.domain.repositories.IAppsRepository
import com.axtech.appmanager.util.NetworkFlow
import com.axtech.appmanager.util.NetworkResult
import com.axtech.appmanager.util.collectFlow
import com.axtech.downloader.Download
import com.axtech.downloader.DownloadState
import com.axtech.downloader.Downloader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class AppsRepository(
    private val appsService: AppsService,
    private val context: Context,
    private val downloader: Downloader
) : IAppsRepository {

    private val downloads = hashMapOf<Int, Download>()

    override fun getAllApps(): NetworkFlow<List<App>> {
        return appsService.getAllApps().collectFlow { response ->
            response.apps.map(AppDto::map)
        }
    }

    override fun cancelDownload(id: Int) {
        downloads[id]?.let { download ->
            downloader.cancel(download.id)
        }
    }

    override fun downloadApp(id: Int, fullPath: String, filename: String): Flow<DownloadState> {

        val filesDir = ContextCompat.getExternalFilesDirs(context, null)[0].absolutePath
        val folder = File(filesDir, "apps")
        val file = File(folder, filename)

        if (file.exists()) {
            file.delete()
        }

        folder.mkdirs()
        file.createNewFile()

        val download = downloader.download(fullPath, file)
        downloads[id] = download

        return download.progress
    }
}