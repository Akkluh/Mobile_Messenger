package com.example.messenger.domain.usecase

import com.example.messenger.domain.repository.ChatRepository
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.repository.MessageRepository

class SendMessageUseCase(private val messageRepository: MessageRepository) {
    suspend fun execute(chatId: Int, text: String): Unit {
        return messageRepository.send(chatId, text)
    }
}