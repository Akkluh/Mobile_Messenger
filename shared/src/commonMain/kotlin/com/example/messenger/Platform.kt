package com.example.messenger

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform