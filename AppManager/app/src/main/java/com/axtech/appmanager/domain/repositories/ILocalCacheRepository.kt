package com.axtech.appmanager.domain.repositories

interface ILocalCacheRepository {
    fun findApp(filename: String): String?
}