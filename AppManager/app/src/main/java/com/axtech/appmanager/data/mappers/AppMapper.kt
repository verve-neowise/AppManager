package com.axtech.appmanager.data.mappers

import com.axtech.appmanager.data.dto.AppDto
import com.axtech.appmanager.domain.models.App
import java.lang.IllegalArgumentException

fun AppDto.map() = App(
    id = id,
    name = name,
    tags = tags.split(":"),
    changelog = changelog,
    downloadPath = downloadPath,
    flag = try {
        App.Flag.valueOf(flag.replaceFirstChar { it.uppercaseChar() })
    } catch (e: IllegalArgumentException) {
        App.Flag.Bugfix
    },
    owner = owner,
    versionCode = versionCode,
    versionNumber = versionNumber
)