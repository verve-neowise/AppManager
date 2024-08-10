package com.axtech.appmanager.di

import com.axtech.appmanager.domain.usecases.CancelDownloadUseCase
import com.axtech.appmanager.domain.usecases.DownloadAppUseCase
import com.axtech.appmanager.domain.usecases.FindAppFromCacheUseCase
import com.axtech.appmanager.domain.usecases.GetAllAppsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAllAppsUseCase(get()) }
    factory { DownloadAppUseCase(get()) }
    factory { CancelDownloadUseCase(get()) }
    factory { FindAppFromCacheUseCase(get()) }
}
