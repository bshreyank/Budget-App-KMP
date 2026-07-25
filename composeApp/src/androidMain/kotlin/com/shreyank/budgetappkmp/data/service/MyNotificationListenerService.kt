package com.shreyank.budgetappkmp.data.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.shreyank.budgetappkmp.data.model.NotificationData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyNotificationListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.let {
            val packageName = it.packageName
            val extras = it.notification.extras
            val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
            val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""
            
            val isTransaction = title.contains("debited", ignoreCase = true) ||
                                title.contains("credited", ignoreCase = true) ||
                                text.contains("debited", ignoreCase = true) ||
                                text.contains("credited", ignoreCase = true) ||
                                bigText.contains("debited", ignoreCase = true) ||
                                bigText.contains("credited", ignoreCase = true)
            
            if (isTransaction) {
                val fullText = if (bigText.length > text.length) bigText else text
                val appName = getAppName(packageName)
                val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
                val formattedTime = timeFormat.format(Date(it.postTime))

                val notificationData = NotificationData(
                    id = it.key,
                    packageName = packageName,
                    appName = appName,
                    title = title,
                    text = fullText,
                    postTime = it.postTime,
                    formattedTime = formattedTime
                )
                
                addNotification(notificationData)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        try {
            val activeList = activeNotifications ?: return
            val list = activeList.mapNotNull { sbn ->
                val extras = sbn.notification.extras
                val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
                val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
                val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""
                
                val isTransaction = title.contains("debited", ignoreCase = true) ||
                                    title.contains("credited", ignoreCase = true) ||
                                    text.contains("debited", ignoreCase = true) ||
                                    text.contains("credited", ignoreCase = true) ||
                                    bigText.contains("debited", ignoreCase = true) ||
                                    bigText.contains("credited", ignoreCase = true)

                if (!isTransaction) return@mapNotNull null

                val fullText = if (bigText.length > text.length) bigText else text
                val appName = getAppName(sbn.packageName)
                val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
                val formattedTime = timeFormat.format(Date(sbn.postTime))

                NotificationData(
                    id = sbn.key,
                    packageName = sbn.packageName,
                    appName = appName,
                    title = title,
                    text = fullText,
                    postTime = sbn.postTime,
                    formattedTime = formattedTime
                )
            }
            updateActiveNotifications(list)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAppName(packageName: String): String {
        val pm = packageManager
        return try {
            val info = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(info).toString()
        } catch (e: Exception) {
            packageName.split('.').lastOrNull()?.replaceFirstChar { it.uppercase() } ?: packageName
        }
    }

    companion object {
        private val _notifications = MutableStateFlow<List<NotificationData>>(emptyList())
        val notifications = _notifications.asStateFlow()

        fun addNotification(notification: NotificationData) {
            _notifications.value = (listOf(notification) + _notifications.value)
                .distinctBy { it.id }
                .sortedByDescending { it.postTime }
        }

        fun updateActiveNotifications(list: List<NotificationData>) {
            val merged = (list + _notifications.value)
                .distinctBy { it.id }
                .sortedByDescending { it.postTime }
            _notifications.value = merged
        }

        fun clearNotifications() {
            _notifications.value = emptyList()
        }
    }
}
