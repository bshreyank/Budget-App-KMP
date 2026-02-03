package com.shreyank.budgetappkmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shreyank.budgetappkmp.ui.components.ActionRow
import com.shreyank.budgetappkmp.ui.components.BalanceCard
import com.shreyank.budgetappkmp.ui.components.TransactionItem
import com.shreyank.budgetappkmp.ui.theme.PrimaryDark
import com.shreyank.budgetappkmp.ui.theme.TextSecondary
import com.shreyank.budgetappkmp.ui.theme.TextWhite

@Composable
fun HomePage(viewModel: HomeViewModel) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = PrimaryDark
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 80.dp) // Space for bottom nav if added later
        ) {
            // 1. Top Bar / Header
            item {
                Spacer(modifier = Modifier.height(20.dp))
                HomeHeader(userName = state.userName)
                Spacer(modifier = Modifier.height(32.dp))
            }

            // 2. Balance Card
            item {
                BalanceCard(
                    totalBalance = state.balance,
                    income = state.income,
                    expense = state.expense
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            // 3. Action Buttons
            item {
                ActionRow()
                Spacer(modifier = Modifier.height(32.dp))
            }

            // 4. Transactions Section Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextWhite
                    )
                    TextButton(onClick = { /* TODO: Navigate to All Transactions */ }) {
                        Text(
                            text = "See All",
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 5. Transactions List
            items(state.recentTransactions) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
    }
}

@Composable
fun HomeHeader(userName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good Morning,",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
            )
        }

        // Profile & Notification Placeholder
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextWhite
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray), // Placeholder for profile image
                contentAlignment = Alignment.Center
            ) {
                // Replace with AsyncImage if Coil is available, or Image with painterResource
                Text("S", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}