package com.axtech.appmanager.ui.presentation.screens.apps

import androidx.compose.runtime.MutableState
import com.axtech.appmanager.domain.models.App
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

interface AppsContract {

    data class AppPreview(
        val id: Int,
        val name: String,
        val versionCode: String,
        val versionNumber: String,
        val tags: List<String>,
        val flag: App.Flag,
        val changelog: String,
        val downloadPath: String?,
        val owner: String,
        val isDownloading: MutableState<Boolean>,
        val downloadProgress: MutableState<Float>,
        var localPath: String?,
    )

    data class State(
        val apps: List<AppPreview> = listOf(),
        val isLoading: Boolean = false,
        val openAppEvent: StateEventWithContent<Int> = consumed(),
        val errorMessageEvent: StateEventWithContent<String> = consumed(),
    )

    sealed class UIEvent {
        object GetApps : UIEvent()
        data class InstallApp(val app: AppPreview) : UIEvent()
        data class DownloadApp(val app: AppPreview) : UIEvent()
        data class DeleteApp(val app: AppPreview) : UIEvent()
        data class CancelDownload(val app: AppPreview): UIEvent()
        object OpenAppEventConsumed : UIEvent()
        object ErrorMessageEventConsumed : UIEvent()
    }
}