package com.axtech.appmanager.ui.presentation.screens.apps

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axtech.appmanager.domain.usecases.CancelDownloadUseCase
import com.axtech.appmanager.domain.usecases.DownloadAppUseCase
import com.axtech.appmanager.domain.usecases.FindAppFromCacheUseCase
import com.axtech.appmanager.domain.usecases.GetAllAppsUseCase
import com.axtech.appmanager.util.NetworkResult
import com.axtech.downloader.DownloadState
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class AppsViewModel(
    private val getAllAppsUseCase: GetAllAppsUseCase,
    private val downloadAppUseCase: DownloadAppUseCase,
    private val cancelDownloadUseCase: CancelDownloadUseCase,
    private val findAppFromCacheUseCase: FindAppFromCacheUseCase
) : ViewModel() {

    var state by mutableStateOf(AppsContract.State())

    private val downloadJobs = HashMap<String, Job>()

    init {
        getApps()
    }

    fun handleUIEvent(event: AppsContract.UIEvent) {
        when (event) {
            AppsContract.UIEvent.GetApps -> {
                getApps()
            }

            is AppsContract.UIEvent.CancelDownload -> {
                cancelDownload(event.app)

                event.app.localPath?.let { localPath ->
                    File(localPath).delete()
                }

                state = state.copy(
                    apps = state.apps.map { app ->
                        if (app.id == event.app.id) {
                            app.copy(
                                localPath = findAppFromCacheUseCase(app.downloadPath)
                            )
                        }
                        else {
                            app
                        }
                    }
                )
            }

            is AppsContract.UIEvent.DownloadApp -> {
                downloadApp(event.app)
            }

            is AppsContract.UIEvent.InstallApp -> {
                event.app.localPath?.let { localPath ->
                    installApp(localPath)
                }
            }
            is AppsContract.UIEvent.DeleteApp -> {
                event.app.localPath?.let { localPath ->
                    File(localPath).delete()
                }
                state = state.copy(
                    apps = state.apps.map { app ->
                        if (app.id == event.app.id) {
                            app.copy(
                                localPath = findAppFromCacheUseCase(app.downloadPath)
                            )
                        }
                        else {
                            app
                        }
                    }
                )
            }

            AppsContract.UIEvent.ErrorMessageEventConsumed -> {
                state = state.copy(
                    errorMessageEvent = consumed()
                )
            }

            AppsContract.UIEvent.OpenAppEventConsumed -> {
                state = state.copy(
                    openAppEvent = consumed()
                )
            }
        }
    }

    private fun cancelDownload(app: AppsContract.AppPreview) {
       downloadJobs[app.downloadPath]?.cancel()
        app.isDownloading.value = false
        app.downloadProgress.value = 0f
        cancelDownloadUseCase(app.id)
    }

    private fun getApps() {
        viewModelScope.launch {
            getAllAppsUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessageEvent = triggered(result.message)
                        )
                    }

                    is NetworkResult.Pending -> {
                        state = state.copy(
                            isLoading = true,
                            errorMessageEvent = consumed()
                        )
                    }

                    is NetworkResult.Success -> {
                        state = state.copy(
                            apps = result.data.map {
                                AppsContract.AppPreview(
                                    id = it.id,
                                    name = it.name,
                                    tags = it.tags,
                                    changelog = it.changelog,
                                    downloadPath = it.downloadPath,
                                    versionCode = it.versionCode,
                                    versionNumber = it.versionNumber,
                                    flag = it.flag,
                                    owner = it.owner,
                                    downloadProgress = mutableFloatStateOf(0f),
                                    isDownloading = mutableStateOf(false),
                                    localPath = findAppFromCacheUseCase(it.downloadPath)
                                )
                            },
                            isLoading = false,
                            errorMessageEvent = consumed()
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun installApp(localPath: String) {
        val command = "pm install -r $localPath"

        val runtime = Runtime.getRuntime()
        val process = runtime.exec("su")

        process.outputStream.write(command.toByteArray())
        process.outputStream.close()
    }

    private fun downloadApp(app: AppsContract.AppPreview) {

        if (app.downloadPath == null) return

        val job = viewModelScope.launch {
            app.isDownloading.value = true
            app.downloadProgress.value = -1f

            downloadAppUseCase(app.id, app.downloadPath)
                .collect { result ->
                    when (result) {
                        is DownloadState.Failure -> {
                            app.isDownloading.value = false
                        }
                        is DownloadState.Loading -> {
                            app.downloadProgress.value = result.progress
                        }
                        is DownloadState.Completed -> {
                            app.localPath = findAppFromCacheUseCase(app.downloadPath)
                            app.isDownloading.value = false
                        }
                        else -> {}
                    }
                }
        }
        downloadJobs[app.downloadPath] = job
    }
}