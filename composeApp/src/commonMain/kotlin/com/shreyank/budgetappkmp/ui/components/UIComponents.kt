package com.shreyank.budgetappkmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shreyank.budgetappkmp.data.model.NotificationData
import com.shreyank.budgetappkmp.data.parser.TransactionType
import kotlin.math.abs

@Composable
fun NotificationCard(notification: NotificationData) {
    val appColor = remember(notification.appName) { getAppColor(notification.appName) }
    val initials = remember(notification.appName) { getAppInitials(notification.appName) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Card Top: App Details & Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circle App Badge
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(appColor.copy(alpha = 0.2f), CircleShape)
                        .border(1.dp, appColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        color = appColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = notification.appName,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = notification.packageName,
                        color = Color(0xFF64748B),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Text(
                    text = notification.formattedTime,
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF334155), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            val txn = notification.transactionInfo
            if (txn != null) {
                val summaryText = txn.getFormattedSummary(notification.title.ifEmpty { notification.appName })
                val textColor = if (txn.type == TransactionType.CREDITED) Color(0xFF34D399) else Color(0xFFFCA5A5)
                val icon = if (txn.type == TransactionType.CREDITED) "💰 " else "💸 "

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = icon,
                        fontSize = 18.sp
                    )
                    Text(
                        text = summaryText,
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            } else {
                // Card Body: Fallback for standard non-transaction notifications
                if (notification.title.isNotEmpty()) {
                    Text(
                        text = notification.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Text(
                    text = notification.text,
                    color = Color(0xFFE2E8F0),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun HighlightRow(title: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = Color(0xFF818CF8),
            modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = Color(0xFF64748B),
                fontSize = 12.sp
            )
        }
    }
}

fun getAppColor(appName: String): Color {
    val hash = appName.hashCode()
    val colors = listOf(
        Color(0xFFF87171), // Pastel Red
        Color(0xFFFB923C), // Pastel Orange
        Color(0xFFFBBF24), // Pastel Yellow
        Color(0xFF34D399), // Pastel Green
        Color(0xFF22D3EE), // Pastel Cyan
        Color(0xFF60A5FA), // Pastel Blue
        Color(0xFF818CF8), // Pastel Indigo
        Color(0xFFC084FC), // Pastel Purple
        Color(0xFFF472B6)  // Pastel Pink
    )
    val index = abs(hash) % colors.size
    return colors[index]
}

fun getAppInitials(appName: String): String {
    if (appName.isBlank()) return "?"
    val cleanName = appName.trim()
    val parts = cleanName.split("\\s+".toRegex())
    if (parts.size >= 2) {
        val first = parts[0].firstOrNull()?.toString() ?: ""
        val second = parts[1].firstOrNull()?.toString() ?: ""
        return (first + second).uppercase()
    }
    return cleanName.take(2).uppercase()
}
