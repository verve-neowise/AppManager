package com.axtech.appmanager.ui.presentation.screens.apps

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.axtech.appmanager.ui.core.LocalDownloader
import com.axtech.appmanager.ui.presentation.screens.apps.compoents.AppItem
import com.axtech.appmanager.ui.theme.AccentColor
import com.axtech.appmanager.util.installApk
import de.palm.composestateevents.EventEffect
import java.io.File

@Composable
fun AppsScreen(
    state: AppsContract.State,
    sendEvent: (AppsContract.UIEvent) -> Unit,
    navigateToNext: (id: Int) -> Unit
) {
    val context = LocalContext.current

    var targetApp by remember {
        mutableStateOf<AppsContract.AppPreview?>(null)
    }

    EventEffect(event = state.openAppEvent, onConsumed = {
        sendEvent(AppsContract.UIEvent.OpenAppEventConsumed)
    }) { id ->
       navigateToNext(id)
    }

    EventEffect(event = state.errorMessageEvent, onConsumed = {
        sendEvent(AppsContract.UIEvent.OpenAppEventConsumed)
    }) { error ->
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = AccentColor
            )
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.apps, key = { it.id }) { app ->
                    AppItem(
                        app = app,
                        open = {
                           targetApp = it
                        },
                        download = {
                            sendEvent(AppsContract.UIEvent.DownloadApp(app))
                        },
                        install = {
                            app.localPath?.let { localPath ->
                                context.installApk(File(localPath))
                            }
                        },
                        deleteApp = {
                            sendEvent(AppsContract.UIEvent.DeleteApp(it))
                        },
                        cancel = {
                            sendEvent(AppsContract.UIEvent.CancelDownload(app))
                        }
                    )
                }
            }
        }
    }

    targetApp?.let { app ->
        ChangelogDialog(app) {
            targetApp = null
        }
    }
}