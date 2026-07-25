package com.shreyank.budgetappkmp.data.model

import com.shreyank.budgetappkmp.data.parser.TransactionInfo
import com.shreyank.budgetappkmp.data.parser.TransactionParser

data class NotificationData(
    val id: String,
    val packageName: String,
    val appName: String,
    val title: String,
    val text: String,
    val postTime: Long,
    val formattedTime: String
) {
    val transactionInfo: TransactionInfo?
        get() = TransactionParser.parse(title, text)
}
