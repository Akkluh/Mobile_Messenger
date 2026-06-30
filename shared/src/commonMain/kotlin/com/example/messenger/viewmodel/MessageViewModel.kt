package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import com.example.messenger.domain.usecase.LoadMessagesUseCase
import com.example.messenger.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MessageViewModel(private val sendMessageUseCase: SendMessageUseCase,
    private val loadMessagesUseCase: LoadMessagesUseCase): ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _loaded = MutableStateFlow(false)
    val loaded = _loaded.asStateFlow()
    fun sendMessage(chatId: Int, text: String, sender: User) {
        viewModelScope.launch {
            sendMessageUseCase.execute(chatId, text, sender)
            val updatedHistory = loadMessagesUseCase.execute(chatId)
            _messages.value = updatedHistory
        }
    }
    fun loadMessages(chatId: Int) {
        viewModelScope.launch {
            _loading.value = true
            delay(500)
            val history = loadMessagesUseCase.execute(chatId)
            _messages.value = history
            _loading.value = false
            _loaded.value = true
        }
    }
}