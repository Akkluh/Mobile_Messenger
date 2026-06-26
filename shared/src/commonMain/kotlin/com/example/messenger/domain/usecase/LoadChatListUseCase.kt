package com.example.messenger.domain.usecase

import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.repository.ChatRepository

class LoadChatListUseCase(private val chatRepository: ChatRepository) {
    suspend fun execute(): List<Chat> {
        return chatRepository.getChatList()
    }
}