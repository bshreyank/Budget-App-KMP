package com.shreyank.budgetappkmp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Subscriptions
import androidx.lifecycle.ViewModel
import com.shreyank.budgetappkmp.ui.components.Transaction
import com.shreyank.budgetappkmp.ui.components.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Simulate loading data
        loadData()
    }

    private fun loadData() {
        _uiState.value = HomeUiState(
            balance = "$12,450.00",
            income = "$4,200",
            expense = "$1,850",
            userName = "Shreyank",
            recentTransactions = listOf(
                Transaction("1", "Netflix Subscription", "Today, 10:00 AM", "$15.00", TransactionType.EXPENSE, Icons.Default.Subscriptions),
                Transaction("2", "Grocery Shopping", "Yesterday, 6:30 PM", "$120.50", TransactionType.EXPENSE, Icons.Default.ShoppingCart),
                Transaction("3", "Salary Credited", "Oct 28, 9:00 AM", "$3,500.00", TransactionType.INCOME, Icons.Default.Fastfood) // Using placeholder icon
            )
        )
    }
}