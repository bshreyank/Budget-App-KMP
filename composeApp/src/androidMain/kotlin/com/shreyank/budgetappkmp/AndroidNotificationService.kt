package com.shreyank.budgetappkmp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import kotlinx.coroutines.flow.StateFlow

class AndroidNotificationService(private val context: Context) : NotificationService {
    override val notifications: StateFlow<List<NotificationData>>
        get() = MyNotificationListenerService.notifications

    override fun isAccessGranted(): Boolean {
        val cn = ComponentName(context, MyNotificationListenerService::class.java)
        val flat = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(cn.flattenToString())
    }

    override fun openSettings() {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    override fun clearNotifications() {
        MyNotificationListenerService.clearNotifications()
    }
}
