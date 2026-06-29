package com.example.messenger.data.repository

import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.repository.ChatRepository

class ChatRepositoryImpl: ChatRepository {
    override suspend fun getChatList(): List<Chat> {
        return try {
            listOf(
                Chat(1, "Капуста", participants = listOf(), messages = listOf()),
                Chat(2, "Огурец", participants = listOf(), messages = listOf())
            )
        } catch (e: Exception) {
            println("ChatRepository: Ошибка закрузки списка чатов: ${e.localizedMessage}")
            emptyList()
        }
    }
}