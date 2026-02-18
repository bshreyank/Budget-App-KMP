package com.shreyank.budgetappkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shreyank.budgetappkmp.data.TransactionRepository
import com.shreyank.budgetappkmp.ui.HomePage
import com.shreyank.budgetappkmp.ui.HomeViewModel

@Composable
fun App(repository: TransactionRepository) {
    MaterialTheme {
        val viewModel = remember {
            HomeViewModel(
                repository = repository
            )
        }
        HomePage(viewModel)
    }
}