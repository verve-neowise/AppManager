package com.axtech.appmanager.domain.usecases

import com.axtech.appmanager.BuildConfig
import com.axtech.appmanager.domain.repositories.IAppsRepository
import com.axtech.appmanager.util.NetworkFlow
import com.axtech.downloader.DownloadState
import kotlinx.coroutines.flow.Flow

class DownloadAppUseCase(
    private val appsRepository: IAppsRepository
) {
    operator fun invoke(id: Int, filename: String): Flow<DownloadState> {
        return appsRepository.downloadApp(id, BuildConfig.SERVER_URL + "apps/$id/download", filename)
    }
}