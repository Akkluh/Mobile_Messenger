package com.example.messenger.domain.repository

import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User

interface MessageRepository {
    suspend fun send(chatId: Int, text: String, sender: User): Unit
    suspend fun load(chatId: Int): List<Message>
}