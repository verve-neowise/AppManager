package com.axtech.appmanager.di

import com.axtech.appmanager.data.client.AppsService
import com.axtech.appmanager.data.client.AuthorizationInterceptor
import com.axtech.appmanager.data.client.RetrofitFactory
import com.axtech.appmanager.data.repositories.AppsRepository
import com.axtech.appmanager.domain.repositories.IAppsRepository
import com.axtech.downloader.Downloader
import org.koin.dsl.module

val networkModule = module {
    single { AuthorizationInterceptor(get()) }
    single { RetrofitFactory.get(get()) }
    single { RetrofitFactory.createService(get(), AppsService::class.java) }
    single { Downloader.getInstance(get()) }
}