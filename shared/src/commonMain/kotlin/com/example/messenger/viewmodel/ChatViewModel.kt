package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.usecase.LoadChatListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ChatViewModel(private val loadChatListUseCase: LoadChatListUseCase) : ViewModel() {
    private val _chatList = MutableStateFlow<List<Chat>>(emptyList())
    val chatList: StateFlow<List<Chat>> = _chatList.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    fun loadChatList() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                // throw IOException("Сеть недоступна")
                _chatList.value = loadChatListUseCase.execute()
            } catch (e: IOException) {
                _errorMessage.value = "Не удалось загрузить чаты. Проверьте подключение."
            } catch (e: Exception) {
                _errorMessage.value = "Произошла ошибка при загрузке данных"
            }
        }
    }
}