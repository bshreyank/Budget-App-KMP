package com.shreyank.budgetappkmp.data.service

import com.shreyank.budgetappkmp.data.model.NotificationData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

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
