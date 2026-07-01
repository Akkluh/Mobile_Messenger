package com.example.messenger.domain.usecase

import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.MessageRepository

class SendMessageUseCase(private val messageRepository: MessageRepository) {
    suspend fun execute(chatId: Int, text: String, sender: User): Unit {
        return messageRepository.send(chatId, text, sender)
    }
}