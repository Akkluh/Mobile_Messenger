package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.repository.ChatRepository
import com.example.messenger.domain.usecase.LoadChatListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val loadChatListUseCase: LoadChatListUseCase) : ViewModel() {
    private val _chatList = MutableStateFlow<List<Chat>>(emptyList())
    val chatList: StateFlow<List<Chat>> = _chatList.asStateFlow()
    fun loadChatList() {
        viewModelScope.launch {
            _chatList.value = loadChatListUseCase.execute()
        }
    }
}