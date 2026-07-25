package com.shreyank.budgetappkmp

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shreyank.budgetappkmp.data.service.NotificationServiceProvider
import com.shreyank.budgetappkmp.ui.navigation.Screen
import com.shreyank.budgetappkmp.ui.screens.home.HomeScreen
import com.shreyank.budgetappkmp.ui.screens.insights.InsightsScreen
import com.shreyank.budgetappkmp.ui.screens.notifications.NotificationsScreen
import com.shreyank.budgetappkmp.ui.screens.settings.SettingsScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun App() {
    val service = NotificationServiceProvider.service
    val notifications by service.notifications.collectAsState()
    var isPermissionGranted by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    // Poll for permission status to automatically update when user returns from settings
    LaunchedEffect(Unit) {
        while (true) {
            isPermissionGranted = service.isAccessGranted()
            delay(1.seconds)
        }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0F172A) // Deep Slate dark background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color(0xFF0F172A),
                bottomBar = {
                    NavigationBar(
                        containerColor = Color(0xFF1E293B),
                        contentColor = Color.White,
                        tonalElevation = 8.dp
                    ) {
                        Screen.entries.forEach { screen ->
                            val selected = currentScreen == screen
                            NavigationBarItem(
                                selected = selected,
                                onClick = { currentScreen = screen },
                                icon = {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = screen.title,
                                        tint = if (selected) Color(0xFF818CF8) else Color(0xFF64748B)
                                    )
                                },
                                label = {
                                    Text(
                                        text = screen.title,
                                        color = if (selected) Color(0xFF818CF8) else Color(0xFF64748B),
                                        fontSize = 12.sp
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(0xFF312E81)
                                )
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Crossfade(
                        targetState = currentScreen,
                        label = "TabCrossfade"
                    ) { screen ->
                        when (screen) {
                            Screen.Home -> HomeScreen(
                                notifications = notifications,
                                onNavigateToNotifications = { currentScreen = Screen.Notifications },
                                onNavigateToInsights = { currentScreen = Screen.Insights }
                            )
                            Screen.Insights -> InsightsScreen(
                                notifications = notifications
                            )
                            Screen.Notifications -> NotificationsScreen(
                                notifications = notifications,
                                onClearAll = { service.clearNotifications() },
                                onOpenSettings = { service.openSettings() }
                            )
                            Screen.Settings -> SettingsScreen(
                                isPermissionGranted = isPermissionGranted,
                                notificationsCount = notifications.size,
                                onOpenSettings = { service.openSettings() },
                                onClearAll = { service.clearNotifications() }
                            )
                        }
                    }
                }
            }
        }
    }
}
