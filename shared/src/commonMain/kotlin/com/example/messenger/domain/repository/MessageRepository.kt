package com.example.messenger.domain.repository

import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun send(chatId: Int, text: String, sender: User): Unit
    fun observeMessages(chatId: Int, currentUserId: Int): Flow<List<Message>>
}