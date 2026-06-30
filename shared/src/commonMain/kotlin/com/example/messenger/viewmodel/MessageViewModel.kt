package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import com.example.messenger.domain.usecase.ObserveMessagesUseCase
import com.example.messenger.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessageViewModel(private val sendMessageUseCase: SendMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase): ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _loaded = MutableStateFlow(false)
    val loaded = _loaded.asStateFlow()
    fun sendMessage(chatId: Int, text: String, sender: User) {
        if (text.isBlank()) {
            return
        }
        viewModelScope.launch {
            sendMessageUseCase.execute(chatId, text, sender)
        }
    }
    fun connectToWebSocket(chatId: Int) {
        viewModelScope.launch {
            observeMessagesUseCase.execute(chatId).collectLatest {
                _messages.value = it
                _loaded.value = true
            }
        }
    }
}