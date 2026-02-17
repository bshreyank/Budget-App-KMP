package com.shreyank.budgetappkmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.shreyank.budgetappkmp.ui.theme.AccentPurple
import com.shreyank.budgetappkmp.ui.theme.SurfaceDark
import com.shreyank.budgetappkmp.ui.theme.TextSecondary
import com.shreyank.budgetappkmp.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddTransactionSheet(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {} // Callback to close sheet if needed from inside
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Expense, 1: Income
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Define Categories
    val expenseCategories = listOf("Food", "Transport", "Entertainment", "Shopping", "Healthcare", "Education", "Other")
    val incomeCategories = listOf("Salary", "Freelance", "Gift", "Other")

    // Determine which list to show
    val currentCategories = if (selectedTab == 0) expenseCategories else incomeCategories

    // Clear selection when tab changes
    LaunchedEffect(selectedTab) {
        selectedCategory = null
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceDark)
            .padding(24.dp)
            .padding(bottom = 32.dp), // Extra padding for safety
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "New Transaction",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = TextWhite
        )

        // Expense / Income Tab Switcher
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(SurfaceDark)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TabButton(
                    text = "Expense",
                    isSelected = selectedTab == 0,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedTab = 0 }
                )

                TabButton(
                    text = "Income",
                    isSelected = selectedTab == 1,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedTab = 1 }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Placeholder Input 1: Amount
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentPurple,
                unfocusedBorderColor = TextSecondary,
                focusedLabelColor = AccentPurple,
                unfocusedLabelColor = TextSecondary,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                cursorColor = AccentPurple
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Section using FlowRow
        Text(
            text = "Category",
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            currentCategories.forEach { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedCategory = if (isSelected) null else category
                    },
                    label = { Text(category) },
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AccentPurple,
                        selectedLabelColor = TextWhite,
                        labelColor = TextWhite,
                        containerColor = SurfaceDark,
                        selectedLeadingIconColor = TextWhite
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = TextSecondary,
                        selectedBorderColor = AccentPurple,
                        enabled = true,
                        selected = isSelected
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Placeholder Input 2: Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (optional)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentPurple,
                unfocusedBorderColor = TextSecondary,
                focusedLabelColor = AccentPurple,
                unfocusedLabelColor = TextSecondary,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                cursorColor = AccentPurple
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save Transaction", color = TextWhite, fontWeight = FontWeight.Bold)
        }
    }
}