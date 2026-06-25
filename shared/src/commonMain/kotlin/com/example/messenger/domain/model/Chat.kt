package com.example.messenger.domain.model

data class Chat(
    val id: Int,
    val title: String,
    val participants: List<User>,
    val messages: List<Message>
) {
}