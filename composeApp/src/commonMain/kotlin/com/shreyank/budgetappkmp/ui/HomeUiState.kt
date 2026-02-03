package com.shreyank.budgetappkmp.ui

import com.shreyank.budgetappkmp.ui.components.Transaction

data class HomeUiState(
    val balance: String = "$0.00",
    val income: String = "$0.00",
    val expense: String = "$0.00",
    val recentTransactions: List<Transaction> = emptyList(),
    val userName: String = "Shreyank"
)