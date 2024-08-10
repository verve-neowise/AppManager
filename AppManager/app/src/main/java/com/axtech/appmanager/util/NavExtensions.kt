package com.axtech.appmanager.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun NavHostController.navigateTo(route: String) {
    if (route != currentBackStackEntry?.destination?.route) {
        navigate(route)
    }
}