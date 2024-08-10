package com.axtech.appmanager.di

import com.axtech.appmanager.data.repositories.AppsRepository
import com.axtech.appmanager.data.repositories.LocalCacheRepository
import com.axtech.appmanager.domain.repositories.IAppsRepository
import com.axtech.appmanager.domain.repositories.ILocalCacheRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IAppsRepository> { AppsRepository(get(), get(), get()) }
    single<ILocalCacheRepository> { LocalCacheRepository(get()) }
}