package com.shreyank.budgetappkmp.ui.screens.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shreyank.budgetappkmp.data.model.NotificationData
import com.shreyank.budgetappkmp.data.parser.TransactionType
import com.shreyank.budgetappkmp.ui.components.getAppColor
import com.shreyank.budgetappkmp.ui.components.getAppInitials
import com.shreyank.budgetappkmp.ui.screens.home.formatAmount

data class AppSpendSummary(
    val appName: String,
    val totalSpent: Double,
    val totalReceived: Double,
    val transactionCount: Int
)

@Composable
fun InsightsScreen(
    notifications: List<NotificationData>
) {
    val transactions = remember(notifications) {
        notifications.mapNotNull { it.transactionInfo }
    }

    val totalSpent = remember(transactions) {
        val debits = transactions.filter { it.type == TransactionType.DEBITED }
        debits.fold(0.0) { acc, txn -> acc + txn.amountValue }
    }

    val totalReceived = remember(transactions) {
        val credits = transactions.filter { it.type == TransactionType.CREDITED }
        credits.fold(0.0) { acc, txn -> acc + txn.amountValue }
    }

    val highestTransaction = remember(transactions) {
        if (transactions.isNotEmpty()) {
            transactions.map { it.amountValue }.maxOrNull() ?: 0.0
        } else {
            0.0
        }
    }

    val averageTransaction = remember(transactions) {
        if (transactions.isNotEmpty()) {
            val total = transactions.map { it.amountValue }.fold(0.0) { acc, d -> acc + d }
            total / transactions.size
        } else {
            0.0
        }
    }

    val appSummaries = remember(notifications) {
        notifications
            .groupBy { it.appName }
            .map { (appName, items) ->
                val txns = items.mapNotNull { it.transactionInfo }
                val spent = txns.filter { it.type == TransactionType.DEBITED }.fold(0.0) { acc, txn -> acc + txn.amountValue }
                val received = txns.filter { it.type == TransactionType.CREDITED }.fold(0.0) { acc, txn -> acc + txn.amountValue }
                AppSpendSummary(
                    appName = appName,
                    totalSpent = spent,
                    totalReceived = received,
                    transactionCount = items.size
                )
            }
            .sortedByDescending { it.totalSpent + it.totalReceived }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Screen Header
        item {
            Column {
                Text(
                    text = "Insights & Analytics",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Visual spending patterns and source breakdown",
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp
                )
            }
        }

        // Cash Flow Overview Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF334155), RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E293B)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "CASH FLOW SUMMARY",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val grandTotal = totalSpent + totalReceived
                    val spentRatio = if (grandTotal > 0) (totalSpent / grandTotal).toFloat() else 0.5f

                    // Visual Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(Color(0xFF334155))
                    ) {
                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(if (spentRatio <= 0f) 0.01f else spentRatio)
                                    .background(Color(0xFFEF4444))
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(if (1f - spentRatio <= 0f) 0.01f else 1f - spentRatio)
                                    .background(Color(0xFF34D399))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color(0xFFEF4444), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Debited (Spent)", color = Color(0xFF94A3B8), fontSize = 12.sp)
                            }
                            Text(
                                "₹${formatAmount(totalSpent)}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color(0xFF34D399), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Credited (Received)", color = Color(0xFF94A3B8), fontSize = 12.sp)
                            }
                            Text(
                                "₹${formatAmount(totalReceived)}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // Key Metrics Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Highest Transaction Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("⚡ Peak Txn", color = Color(0xFF94A3B8), fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "₹${formatAmount(highestTransaction)}",
                            color = Color(0xFF60A5FA),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }

                // Avg Transaction Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📈 Avg Txn", color = Color(0xFF94A3B8), fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "₹${formatAmount(averageTransaction)}",
                            color = Color(0xFF818CF8),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        // App-Wise Breakdown Header
        item {
            Text(
                text = "Source & App Distribution",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (appSummaries.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Text(
                        text = "No notification sources recorded yet.",
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(24.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(appSummaries) { summary ->
                val appColor = remember(summary.appName) { getAppColor(summary.appName) }
                val initials = remember(summary.appName) { getAppInitials(summary.appName) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
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

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = summary.appName,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${summary.transactionCount} notification(s)",
                                color = Color(0xFF64748B),
                                fontSize = 12.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            if (summary.totalSpent > 0) {
                                Text(
                                    "-₹${formatAmount(summary.totalSpent)}",
                                    color = Color(0xFFFCA5A5),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            if (summary.totalReceived > 0) {
                                Text(
                                    "+₹${formatAmount(summary.totalReceived)}",
                                    color = Color(0xFF34D399),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            if (summary.totalSpent == 0.0 && summary.totalReceived == 0.0) {
                                Text(
                                    "No amount",
                                    color = Color(0xFF64748B),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
