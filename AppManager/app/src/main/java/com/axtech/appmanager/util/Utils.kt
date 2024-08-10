package com.axtech.appmanager.util

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.FileProvider
import com.axtech.appmanager.BuildConfig
import java.io.File


fun Any?.isNotNull(): Boolean {
    return this != null
}

fun <T, R> T.mapTo(transform: (T) -> R): R = transform(this)

fun Context.installApk(filePath: File) {
    val contentUri = FileProvider.getUriForFile(
        this,
        BuildConfig.APPLICATION_ID + ".provider",
        filePath
    )

    val install = Intent(Intent.ACTION_VIEW)
    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
    install.data = contentUri
    startActivity(install)
}