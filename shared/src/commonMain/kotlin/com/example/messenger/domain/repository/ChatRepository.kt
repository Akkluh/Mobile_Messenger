package com.example.messenger.domain.repository

import com.example.messenger.domain.model.Chat

interface ChatRepository {
    suspend fun getChatList(): List<Chat>
}