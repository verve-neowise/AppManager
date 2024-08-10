package com.axtech.appmanager.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    AppNavigation(
        rootNavController = navController,
    )
}