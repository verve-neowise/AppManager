package com.axtech.appmanager.ui.presentation.screens.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.axtech.appmanager.domain.models.App
import com.axtech.appmanager.ui.presentation.screens.apps.compoents.Flag
import com.axtech.appmanager.ui.theme.Background

@Composable
fun ChangelogDialog(
    app: AppsContract.AppPreview,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Background)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 300.dp)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier,
                    text = "Changelog",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier,
                    text = app.changelog,
                    color = Color.White,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
            }
        }
    }
}

@Composable
@Preview(backgroundColor = 0xff000000, showBackground = true)
private fun ChangelogDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        ChangelogDialog(
            app = AppsContract.AppPreview(
                id = 0,
                versionNumber = "14",
                owner = "Jalol Imomaddinov",
                flag = App.Flag.Production,
                versionCode = "1.23",
                downloadPath = "",
                changelog = "\nWe use ads to keep our content free for you.\n" +
                        "Please allow ads and let sponsors fund your surfing.\n" +
                        "Thank you!\n" +
                        "We use ads to keep our content free for you.\n" +
                        "We use ads to keep our content free for you.\n" +
                        "We use ads to keep our content free for you." +
                        "\nWe use ads to keep our content free for you.\n" +
                        "Please allow ads and let sponsors fund your surfing.\n" +
                        "Thank you!\n" +
                        "We use ads to keep our content free for you.\n" +
                        "We use ads to keep our content free for you.\n" +
                        "We use ads to keep our content free for you."                ,
                tags = listOf("top", "gap"),
                name = "AXCrm 2.0",
                localPath = null,
                isDownloading = remember { mutableStateOf(true) },
                downloadProgress = remember { mutableFloatStateOf(0.5f) }
            )
        ) {
        }
    }
}