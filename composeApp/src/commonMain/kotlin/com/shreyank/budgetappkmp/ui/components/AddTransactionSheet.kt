package com.shreyank.budgetappkmp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shreyank.budgetappkmp.ui.theme.AccentPurple
import com.shreyank.budgetappkmp.ui.theme.SurfaceDark
import com.shreyank.budgetappkmp.ui.theme.TextSecondary
import com.shreyank.budgetappkmp.ui.theme.TextWhite

@Composable
fun AddTransactionSheet(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {} // Callback to close sheet if needed from inside
) {
    var selectedTab by remember { mutableStateOf(0) }

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
            value = "",
            onValueChange = {},
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
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ToDo: Category Chips will come here!
        //  It will change according to the Expense and Income Tabs.

        Spacer(modifier = Modifier.height(16.dp))

        // Placeholder Input 2: Description
        OutlinedTextField(
            value = "",
            onValueChange = {},
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