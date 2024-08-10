package com.axtech.appmanager.ui.presentation.screens.apps

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppsDestination(
    navigateToAppDetails: (id: Int) -> Unit
) {
    val viewModel: AppsViewModel = koinViewModel()
    AppsScreen(
        state = viewModel.state,
        sendEvent = viewModel::handleUIEvent,
        navigateToNext = navigateToAppDetails
    )
}