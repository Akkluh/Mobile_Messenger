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
        val replyMessage = Message(
            id = chatMessage.size,
            text = "Это тестовое сообщение!",
            timestamp = System.currentTimeMillis(),
            sender = User(id = 2, login = "Собеседник")
        )
        chatMessage.add(replyMessage)
    }

    override suspend fun load(chatId: Int): List<Message> {
        val chatMessage = messages.getOrPut(chatId) { mutableListOf() }
        if (chatMessage.isEmpty()) {
            chatMessage.add(
                Message(
                    id = -1,
                    text = "Привет! Это тестовое сообщение!",
                    timestamp = System.currentTimeMillis() - 60000,
                    sender = User(id = 2, login = "Собеседник")
                )
            )
        }
        return chatMessage.toList()
    }
}