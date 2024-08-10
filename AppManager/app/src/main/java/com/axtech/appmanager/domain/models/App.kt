package com.axtech.appmanager.domain.models

class App(
    val id: Int,
    val name: String,
    val versionCode: String,
    val versionNumber: String,
    val tags: List<String>,
    val flag: Flag,
    val changelog: String,
    val downloadPath: String?,
    val owner: String
) {
    enum class Flag {
        Production,
        Feature,
        Bugfix
    }
}