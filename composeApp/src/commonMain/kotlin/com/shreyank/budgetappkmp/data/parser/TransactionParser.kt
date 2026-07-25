package com.shreyank.budgetappkmp.data.parser

enum class TransactionType {
    DEBITED, CREDITED
}

data class TransactionInfo(
    val amount: String,
    val type: TransactionType
) {
    val amountValue: Double
        get() = amount.replace(",", "").toDoubleOrNull() ?: 0.0

    fun getFormattedSummary(bankOrTitle: String): String {
        val bankName = extractBankName(bankOrTitle)
        val action = if (type == TransactionType.DEBITED) "Debited" else "Credited"
        return "$bankName $action : $amount"
    }

    private fun extractBankName(rawStr: String): String {
        val upper = rawStr.uppercase()
        return when {
            upper.contains("IDFC") -> "IDFC"
            upper.contains("SBI") -> "SBI"
            upper.contains("HDFC") -> "HDFC"
            upper.contains("ICICI") -> "ICICI"
            upper.contains("AXIS") -> "AXIS"
            upper.contains("KOTAK") -> "KOTAK"
            upper.contains("PNB") -> "PNB"
            upper.contains("BOB") -> "BOB"
            upper.contains("UNION") -> "UNION"
            else -> rawStr.split(" ").firstOrNull { it.isNotBlank() } ?: "Bank"
        }
    }
}

object TransactionParser {

    private val regexes = listOf(
        // Pattern 1: debited/credited [by/with/for/of] [Rs./INR/₹] 9,000.00
        Regex("""(?i)\b(debited|credited)\b\s*(?:by|with|for|of)?\s*(?:rs\.?|inr|₹)?\s*([\d,]+(?:\.\d{1,2})?)"""),

        // Pattern 2: [Rs./INR/₹] 9,000.00 [is/was] debited/credited
        Regex("""(?i)(?:rs\.?|inr|₹)?\s*([\d,]+(?:\.\d{1,2})?)\s*(?:rs\.?|inr|₹)?\s*(?:is|was)?\s*\b(debited|credited)\b""")
    )

    fun parse(title: String, text: String): TransactionInfo? {
        val combined = "$title $text"

        // Match Pattern 1
        val match1 = regexes[0].find(combined)
        if (match1 != null) {
            val action = match1.groupValues[1].lowercase()
            val rawAmount = match1.groupValues[2]
            if (isValidAmount(rawAmount)) {
                val type = if (action == "debited") TransactionType.DEBITED else TransactionType.CREDITED
                return TransactionInfo(amount = rawAmount, type = type)
            }
        }

        // Match Pattern 2
        val match2 = regexes[1].find(combined)
        if (match2 != null) {
            val rawAmount = match2.groupValues[1]
            val action = match2.groupValues[2].lowercase()
            if (isValidAmount(rawAmount)) {
                val type = if (action == "debited") TransactionType.DEBITED else TransactionType.CREDITED
                return TransactionInfo(amount = rawAmount, type = type)
            }
        }

        return null
    }

    private fun isValidAmount(amountStr: String): Boolean {
        if (amountStr.isBlank()) return false
        val clean = amountStr.replace(",", "")
        val doubleVal = clean.toDoubleOrNull() ?: return false
        return doubleVal > 0
    }
}
