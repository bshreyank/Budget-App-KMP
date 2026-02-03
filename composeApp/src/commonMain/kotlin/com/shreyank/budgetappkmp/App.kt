package com.shreyank.budgetappkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shreyank.budgetappkmp.ui.HomePage
import com.shreyank.budgetappkmp.ui.HomeViewModel

@Composable
fun App() {
    MaterialTheme {
        val viewModel = remember { HomeViewModel() }
        HomePage(viewModel)
    }
}