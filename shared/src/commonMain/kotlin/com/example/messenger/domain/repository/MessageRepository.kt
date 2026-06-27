package com.example.messenger.domain.repository

import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.model.Message

interface MessageRepository {
    suspend fun send(chatId: Int, text: String): Unit
    suspend fun load(chatId: Int): List<Message>
}