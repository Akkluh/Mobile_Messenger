package com.example.messenger.domain.usecase

import com.example.messenger.domain.model.Message
import com.example.messenger.domain.repository.MessageRepository

class LoadMessagesUseCase(private val messageRepository: MessageRepository) {
    suspend fun execute(chatId: Int): List<Message> {
        return messageRepository.load(chatId)
    }
}