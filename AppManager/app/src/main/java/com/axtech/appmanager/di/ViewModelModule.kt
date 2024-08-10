package com.axtech.appmanager.di

import com.axtech.appmanager.ui.presentation.screens.apps.AppsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AppsViewModel(
            getAllAppsUseCase = get(),
            findAppFromCacheUseCase = get(),
            downloadAppUseCase = get(),
            cancelDownloadUseCase = get(),
        )
    }
}