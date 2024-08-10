package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.axtech.appmanager.ui.theme.AccentColor
import com.axtech.appmanager.ui.theme.Background
import com.axtech.appmanager.ui.theme.Black
import com.axtech.appmanager.ui.theme.DisabledTextColor
import com.axtech.appmanager.util.currentRoute
import com.axtech.appmanager.util.navigateTo

data class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun AxBottomNavigation(
    items: List<NavigationItem>,
    navController: NavHostController
) {
    BottomNavigation(
        backgroundColor = Background,
        contentColor = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        val currentRoute = navController.currentRoute()

        items.forEach { item ->
            val selected = currentRoute == item.route
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigateTo(item.route)
                },
                icon = {
                    Box(modifier = Modifier
                        .padding(bottom = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (selected) AccentColor else Color.Transparent)
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "",
                            tint = if (selected) Color.White else DisabledTextColor
                        )
                    }
                },
                label = {
                    Text(text = item.title, color = Color.White)
                }
            )
        }
    }
}

@Composable
fun AxBottomBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Black)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        actions()
    }
}
