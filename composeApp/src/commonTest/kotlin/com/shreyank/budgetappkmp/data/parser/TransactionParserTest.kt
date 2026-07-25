package com.shreyank.budgetappkmp.data.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TransactionParserTest {

    @Test
    fun testIDFCDebited() {
        val title = "IDFC Debited"
        val text = "Your A/c XX8282 debited by Rs. 9,000.00 on 11/07/26; Shreyank Ravindra  B credited. RRN 655803591310. Available balance Rs. 45,000.00. Team IDFC FIRST Bank"

        val parsed = TransactionParser.parse(title, text)
        assertNotNull(parsed)
        assertEquals("9,000.00", parsed.amount)
        assertEquals(TransactionType.DEBITED, parsed.type)
        assertEquals("IDFC Debited : 9,000.00", parsed.getFormattedSummary(title))
    }

    @Test
    fun testIDFCCredited() {
        val title = "IDFC Credited"
        val text = "Your A/C XXXXX268282 is credited with INR 20.00 on 19/07/26 16:28. Your new balance is INR 45,020.00. Team IDFC FIRST Bank"

        val parsed = TransactionParser.parse(title, text)
        assertNotNull(parsed)
        assertEquals("20.00", parsed.amount)
        assertEquals(TransactionType.CREDITED, parsed.type)
        assertEquals("IDFC Credited : 20.00", parsed.getFormattedSummary(title))
    }

    @Test
    fun testSBIDebited() {
        val title = "SBI Debited"
        val text = "Dear UPI user A/C X1335 debited by 334.00 on date 18Jul26 trf to Swish Refno 619975967121 If not u? call-1800111109 for other services-18001234-SBI"

        val parsed = TransactionParser.parse(title, text)
        assertNotNull(parsed)
        assertEquals("334.00", parsed.amount)
        assertEquals(TransactionType.DEBITED, parsed.type)
        assertEquals("SBI Debited : 334.00", parsed.getFormattedSummary(title))
    }

    @Test
    fun testSBICredited() {
        val title = "SBI Credited"
        val text = "Dear SBI User, your A/c X1335-credited by Rs.6910 on 10Jun26 transfer from JAYADITYA JAGDISH GAIKWAD Ref No 616165047795 -SBI"

        val parsed = TransactionParser.parse(title, text)
        assertNotNull(parsed)
        assertEquals("6910", parsed.amount)
        assertEquals(TransactionType.CREDITED, parsed.type)
        assertEquals("SBI Credited : 6910", parsed.getFormattedSummary(title))
    }
}
