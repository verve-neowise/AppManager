package com.axtech.appmanager.ui.presentation.screens.apps.compoents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axtech.appmanager.R
import com.axtech.appmanager.domain.models.App
import com.axtech.appmanager.ui.presentation.screens.apps.AppsContract
import com.axtech.appmanager.ui.presentation.screens.apps.AppsContract.AppPreview
import com.axtech.appmanager.ui.theme.AccentColor
import com.axtech.appmanager.ui.theme.Background

@Composable
fun AppItem(
    app: AppPreview,
    open: (AppPreview) -> Unit,
    deleteApp: (AppPreview) -> Unit,
    download: (AppPreview) -> Unit,
    install: (AppPreview) -> Unit,
    cancel: (AppPreview) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = { open(app) },
            )
            .background(Background)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = app.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Flag(app.flag)
            }
            Text(
                text = "Version code: ${app.versionCode}",
                color = Color(0x80ffffff),
                fontSize = 12.sp
            )
            Text(
                text = "Version number: ${app.versionNumber}",
                color = Color(0x80ffffff),
                fontSize = 12.sp
            )
        }
        Divider(color = Color(0x26ffffff))
        Text(
            modifier = Modifier
                .padding(10.dp)
                .requiredHeightIn(0.dp, 64.dp),
            text = app.changelog,
            color = Color.White,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis
        )
        Divider(color = Color(0x26ffffff))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(80.dp))
                    .size(24.dp)
                    .background(Color(0xfffed7aa)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.owner.first().toString(),
                    color = Color(0xff431407),
                    fontSize = 12.sp,
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = app.owner,
                color = Color.White,
                fontSize = 12.sp,
            )

            when {
                app.isDownloading.value -> {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (app.downloadProgress.value == -1f) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = AccentColor,
                                strokeWidth = 2.dp,
                            )
                        }
                        else {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = AccentColor,
                                strokeWidth = 2.dp,
                                progress = app.downloadProgress.value
                            )
                        }

                        IconButton(
                            modifier = Modifier.size(16.dp),
                            onClick = { cancel(app) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "clear",
                                tint = AccentColor
                            )
                        }
                    }
                }
                app.localPath != null -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledIconButton(
                            modifier = Modifier.size(30.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color(0xff0070f0),
                                contentColor = Color.White
                            ),
                            onClick = { deleteApp(app) }
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.trash),
                                contentDescription = ""
                            )
                        }
                        Button(
                            modifier = Modifier.height(30.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff0070f0),
                                contentColor = Color.White
                            ),
                            onClick = { install(app) }
                        ) {
                            Text(text = "Install", fontSize = 12.sp)
                        }
                    }
                }
                else -> {
                    FilledIconButton(
                        modifier = Modifier.size(30.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color(0xff0070f0),
                            contentColor = Color.White
                        ),
                        onClick = { download(app) }
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xff000000)
private fun AppItemPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        AppItem(
            app = AppPreview(
                id = 0,
                versionNumber = "14",
                owner = "Jalol Imomaddinov",
                flag = App.Flag.Production,
                versionCode = "1.23",
                downloadPath = "",
                changelog = "Changelog:\nWe use ads to keep our content free for you.\n" +
                        "Please allow ads and let sponsors fund your surfing.\n" +
                        "Thank you!",
                tags = listOf("top", "gap"),
                name = "AXCrm 2.0",
                localPath = null,
                isDownloading = remember { mutableStateOf(true) },
                downloadProgress = remember { mutableFloatStateOf(0.5f) }
            ),
            cancel = {
            },
            download = {
            },
            install = {
            },
            deleteApp = {

            },
            open = {

            }
        )
    }
}