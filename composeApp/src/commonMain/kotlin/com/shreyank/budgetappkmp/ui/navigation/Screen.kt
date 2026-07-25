package com.shreyank.budgetappkmp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : Screen("home", "Home", Icons.Default.Home)
    data object Insights : Screen("insights", "Insights", Icons.Default.Star)
    data object Notifications : Screen("notifications", "Notifications", Icons.Default.Notifications)
    data object Settings : Screen("settings", "Settings", Icons.Default.Settings)

    companion object {
        val entries get() = listOf(Home, Insights, Notifications, Settings)
    }
}
