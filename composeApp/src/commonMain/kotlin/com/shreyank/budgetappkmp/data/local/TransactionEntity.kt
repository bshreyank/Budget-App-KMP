package com.shreyank.budgetappkmp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val category: String,
    val description: String,
    val type: String,   // Income or Expense
    val timestamp: Long
)