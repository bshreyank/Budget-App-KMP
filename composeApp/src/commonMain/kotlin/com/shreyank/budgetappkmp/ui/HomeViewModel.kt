package com.shreyank.budgetappkmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyank.budgetappkmp.data.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TransactionRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.transactions
        .map { transactions ->
            HomeUiState(
                recentTransactions = transactions,
                balance = calculateBalance(transactions), // Implement this calculation logic
                income = calculateIncome(transactions),
                expense = calculateExpense(transactions)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    fun saveTransaction(amount: String, category: String, description: String, isExpense: Boolean) {
        viewModelScope.launch {
            val amountDouble = amount.toDoubleOrNull() ?: 0.0
            repository.saveTransaction(amountDouble, category, description, isExpense)
        }
    }

    // Helper functions for totals would go here
    private fun calculateBalance(list: List<com.shreyank.budgetappkmp.ui.components.Transaction>): String = "$0.00"
    private fun calculateIncome(list: List<com.shreyank.budgetappkmp.ui.components.Transaction>): String = "$0.00"
    private fun calculateExpense(list: List<com.shreyank.budgetappkmp.ui.components.Transaction>): String = "$0.00"
}