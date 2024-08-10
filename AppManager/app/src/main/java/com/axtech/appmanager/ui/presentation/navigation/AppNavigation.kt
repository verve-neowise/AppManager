package com.axtech.appmanager.ui.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.axtech.appmanager.ui.presentation.components.EmptyScreen
import com.axtech.appmanager.ui.presentation.components.NavigationItem
import com.axtech.appmanager.ui.presentation.screens.apps.AppsDestination
import com.axtech.appmanager.ui.presentation.screens.main.MainScreen

val navItems = listOf(
    NavigationItem(
        route = "/apps",
        title = "Apps",
        icon = Icons.Outlined.Home
    ),
    NavigationItem(
        route = "/info",
        title = "About",
        icon = Icons.Filled.Info
    ),
)

@Composable
fun AppNavigation(
    rootNavController: NavHostController
) {
    NavHost(
        rootNavController,
        startDestination = "/main"
    ) {

        composable(
            route = "/main",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
        ) {

            val mainNavController = rememberNavController()

            MainScreen(
                navController = mainNavController,
                navItems = navItems,
            ) {
                MainNavigation(
                    navController = mainNavController,
                )
            }
        }
        composable(
            route = "/app/{id}/details",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
        ) {

        }
    }
}


@Composable
fun MainNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "/apps"
    ) {
        composable(
            route = "/apps",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(300)
                )
            },
        ) {
            AppsDestination(
                navigateToAppDetails = {}
            )
        }
        composable(
            route = "/info",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(300)
                )
            },
        ) {
            EmptyScreen("Info")
        }
    }
}