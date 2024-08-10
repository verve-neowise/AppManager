package com.axtech.appmanager.data.repositories

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.axtech.appmanager.domain.repositories.ILocalCacheRepository
import java.io.File

class LocalCacheRepository(private val context: Context) : ILocalCacheRepository {

    override fun findApp(filename: String): String? {

        val filesDir = ContextCompat.getExternalFilesDirs(context, null)[0].absolutePath
        val folder = "$filesDir${File.separator}apps${File.separator}"
        val file = File(folder, filename)

        Log.d("FindApp:", file.absolutePath + " ${file.exists()}")
        return if (file.exists()) file.absolutePath else null
    }
}