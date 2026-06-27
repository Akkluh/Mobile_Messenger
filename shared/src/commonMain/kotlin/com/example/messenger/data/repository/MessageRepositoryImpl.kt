package com.example.messenger.data.repository

import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.MessageRepository

class MessageRepositoryImpl: MessageRepository {
    private val messages = mutableMapOf<Int, MutableList<Message>>()
    override suspend fun send(chatId: Int, text: String) {
        val message = Message(id = messages[chatId]?.size ?: 0, text = text, timestamp = System.currentTimeMillis(), sender = User(id = 1, login = "admin"))
        val chatMessage = messages.getOrPut(chatId) { mutableListOf() }
        chatMessage.add(message)
    }

    override suspend fun load(chatId: Int): List<Message> {
        return messages[chatId]?.toList() ?: emptyList()
    }
}