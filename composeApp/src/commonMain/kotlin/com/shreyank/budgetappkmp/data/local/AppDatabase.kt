package com.shreyank.budgetappkmp.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [TransactionEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}

// The Room compiler generates the 'actual' implementation
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>