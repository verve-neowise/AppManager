package com.axtech.appmanager.ui.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.axtech.appmanager.R
import com.axtech.appmanager.ui.presentation.components.AxBottomNavigation
import com.axtech.appmanager.ui.presentation.components.NavigationItem
import com.axtech.appmanager.ui.theme.AccentColor
import com.axtech.appmanager.ui.theme.Black


@Composable
fun MainScreen(
    navController: NavHostController,
    navItems: List<NavigationItem>,
    content: @Composable () -> Unit
) {

    Scaffold(
        bottomBar = {
            AxBottomNavigation(
                items = navItems,
                navController = navController
            )
        },
        topBar = {
             TopAppBar(
                 title = {
                     Row(
                         verticalAlignment = Alignment.CenterVertically,
                         horizontalArrangement = Arrangement.spacedBy(10.dp)
                     ) {
                         Icon(
                             painter = painterResource(id = R.drawable.smartphone),
                             contentDescription = "",
                             tint = AccentColor
                         )
                         Text(
                             text = "AX Mobile",
                             color = AccentColor
                         )
                     }
                 },
                 backgroundColor = Color.Transparent,
             )
        },
        backgroundColor = Black
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
}
