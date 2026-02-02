package com.shreyank.budgetappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform