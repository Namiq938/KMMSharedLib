package com.namig.expencecalculator.library

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform