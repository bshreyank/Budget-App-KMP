package com.shreyank.budgetappkmp

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

data class NotificationData(
    val id: String,
    val packageName: String,
    val appName: String,
    val title: String,
    val text: String,
    val postTime: Long,
    val formattedTime: String
)

interface NotificationService {
    val notifications: StateFlow<List<NotificationData>>
    fun isAccessGranted(): Boolean
    fun openSettings()
    fun clearNotifications()
}

class DummyNotificationService : NotificationService {
    override val notifications: StateFlow<List<NotificationData>> = MutableStateFlow(emptyList())
    override fun isAccessGranted(): Boolean = false
    override fun openSettings() {}
    override fun clearNotifications() {}
}

object NotificationServiceProvider {
    var service: NotificationService = DummyNotificationService()
}
