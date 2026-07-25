package com.shreyank.budgetappkmp.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
