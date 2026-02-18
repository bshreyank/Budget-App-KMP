package com.shreyank.budgetappkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.room.Room
import com.shreyank.budgetappkmp.data.TransactionRepository
import com.shreyank.budgetappkmp.data.local.AppDatabase
import com.shreyank.budgetappkmp.data.local.getRoomDatabase
import java.io.File

fun main() = application {
    // 1. Create Database Builder
    val dbFile = File(System.getProperty("java.io.tmpdir"), "budget.db")
    val dbBuilder = Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )

    // 2. Build Database and Repository
    val database = getRoomDatabase(dbBuilder)
    val repository = TransactionRepository(database.transactionDao())

    Window(
        onCloseRequest = ::exitApplication,
        title = "BudgetAppKMP",
    ) {
        // 3. Pass Repository to App
        App(repository)
    }
}