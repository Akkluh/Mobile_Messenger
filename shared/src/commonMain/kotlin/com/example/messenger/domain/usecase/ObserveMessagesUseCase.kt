package com.example.messenger.domain.usecase

import com.example.messenger.domain.model.Message
import com.example.messenger.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

class ObserveMessagesUseCase(private val messageRepository: MessageRepository) {
    fun execute(chatId: Int): Flow<List<Message>> {
        return messageRepository.observeMessages(chatId)
    }
}