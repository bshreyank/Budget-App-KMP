package com.shreyank.budgetappkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.shreyank.budgetappkmp.data.TransactionRepository
import com.shreyank.budgetappkmp.data.local.AppDatabase
import com.shreyank.budgetappkmp.data.local.getRoomDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Create Database Builder
        val dbBuilder = Room.databaseBuilder<AppDatabase>(
            context = applicationContext,
            name = applicationContext.getDatabasePath("budget.db").absolutePath
        )

        // Build Database and Repository
        val database = getRoomDatabase(dbBuilder)
        val repository = TransactionRepository(database.transactionDao())

        setContent {
            App(repository)
        }
    }
}