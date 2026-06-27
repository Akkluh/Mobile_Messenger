package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import com.example.messenger.domain.usecase.LoadMessagesUseCase
import com.example.messenger.domain.usecase.LoginUseCase
import com.example.messenger.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessageViewModel(private val sendMessageUseCase: SendMessageUseCase,
    private val loadMessagesUseCase: LoadMessagesUseCase): ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    fun sendMessage(chatId: Int, text: String) {
        viewModelScope.launch {
            sendMessageUseCase.execute(chatId, text)
            val updatedHistory = loadMessagesUseCase.execute(chatId)
            _messages.value = updatedHistory
        }
    }
    fun loadMessages(chatId: Int) {
        _messages.value = emptyList()
        viewModelScope.launch {
            val history = loadMessagesUseCase.execute(chatId)
            _messages.value = history
        }
    }
}