package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.repository.ChatRepository
import com.example.messenger.domain.usecase.LoadChatListUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class ChatViewModel(private val loadChatListUseCase: LoadChatListUseCase) : ViewModel() {
    val chatList = MutableStateFlow<List<Chat>>(emptyList())
    suspend fun loadChatList() {
        chatList.value = loadChatListUseCase.execute()
    }
}