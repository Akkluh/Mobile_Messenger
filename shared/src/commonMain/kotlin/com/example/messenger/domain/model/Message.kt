package com.example.messenger.domain.model

data class Message(
    val id: Int,
    val text: String,
    val timestamp: Long,
    val sender: User
) {
}