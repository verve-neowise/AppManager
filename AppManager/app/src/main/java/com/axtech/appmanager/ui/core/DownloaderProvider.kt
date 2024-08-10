package com.axtech.appmanager.ui.core

import androidx.compose.runtime.compositionLocalOf
import com.axtech.downloader.Downloader

val LocalDownloader = compositionLocalOf<Downloader> {
    error("Not downloader set")
}