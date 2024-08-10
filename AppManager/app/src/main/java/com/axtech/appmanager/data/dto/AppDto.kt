package com.axtech.appmanager.data.dto

import com.google.gson.annotations.SerializedName

class AppDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("versionCode")
    val versionCode: String,
    @SerializedName("versionNumber")
    val versionNumber: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("changelog")
    val changelog: String,
    @SerializedName("downloadPath")
    val downloadPath: String?,
    @SerializedName("owner")
    val owner: String
)