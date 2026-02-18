package com.shreyank.budgetappkmp.data

import androidx.compose.material.icons.filled.ShoppingCart
import com.shreyank.budgetappkmp.data.local.TransactionDao
import com.shreyank.budgetappkmp.data.local.TransactionEntity
import com.shreyank.budgetappkmp.ui.components.Transaction
import com.shreyank.budgetappkmp.ui.components.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepository(private val dao: TransactionDao) {

    // Transform Entity -> UI Model
    val transactions: Flow<List<Transaction>> = dao.getAllTransactions().map { entities ->
        entities.map { entity ->
            Transaction(
                id = entity.id.toString(),
                title = entity.category, // Using category as title for now
                date = "Just now", // You should format entity.timestamp here
                amount = "$${entity.amount}",
                type = if (entity.type == "INCOME") TransactionType.INCOME else TransactionType.EXPENSE,
                icon = androidx.compose.material.icons.Icons.Default.ShoppingCart // Logic to pick icon based on category
            )
        }
    }

    suspend fun saveTransaction(amount: Double, category: String, description: String, isExpense: Boolean) {
        val entity = TransactionEntity(
            amount = amount,
            category = category,
            description = description,
            type = if (isExpense) "EXPENSE" else "INCOME",
            timestamp = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
        )
        dao.insert(entity)
    }
}