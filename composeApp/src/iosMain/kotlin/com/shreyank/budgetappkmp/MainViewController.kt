package com.shreyank.budgetappkmp

import androidx.compose.ui.window.ComposeUIViewController
import androidx.room.Room
import com.shreyank.budgetappkmp.data.TransactionRepository
import com.shreyank.budgetappkmp.data.local.AppDatabase
import com.shreyank.budgetappkmp.data.local.getRoomDatabase
import com.shreyank.budgetappkmp.data.local.instantiateImpl // KSP Generated
import platform.Foundation.NSHomeDirectory

fun MainViewController() = ComposeUIViewController {
    // 1. Create Database Builder
    val dbFilePath = NSHomeDirectory() + "/budget.db"
    val dbBuilder = Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() } // Requires KSP build to be successful
    )

    // 2. Build Database and Repository
    val database = getRoomDatabase(dbBuilder)
    val repository = TransactionRepository(database.transactionDao())

    // 3. Pass Repository to App
    App(repository)
}