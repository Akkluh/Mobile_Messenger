package com.example.messenger.data.repository

import com.example.messenger.data.network.WebSocketClient
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessageRepositoryImpl: MessageRepository {
    private val webSocketClient = WebSocketClient()
    private val messagesMap = mutableMapOf<Int, MutableList<Message>>()
    private val _messagesFlow = MutableStateFlow<List<Message>>(emptyList())
    private var activeChatId: Int = -1
    init {
        webSocketClient.connect()
        CoroutineScope(Dispatchers.Default).launch {
            webSocketClient.incomingMessages.collect {rawSocketData ->
                val parts = rawSocketData.split(":", limit = 2)
                if (parts.size < 2) {
                    return@collect
                }
                val targetChatId = parts[0].toIntOrNull() ?: 1
                val responseText = parts[1]
                val chatMessages = messagesMap.getOrPut(targetChatId) { mutableListOf() }
                val incomingMessages = Message(
                    id = chatMessages.size + 1,
                    text = responseText,
                    timestamp = System.currentTimeMillis(),
                    sender = User(id = 114, login = "Собеседник")
                )
                chatMessages.add(incomingMessages)
                if (targetChatId == activeChatId) {
                    _messagesFlow.value = chatMessages.toList()
                }
            }
        }
    }
    override suspend fun send(chatId: Int, text: String, sender: User) {
        val chatMessage = messagesMap.getOrPut(chatId) { mutableListOf() }
        val userMessage = Message(
            id = chatMessage.size + 1,
            text = text, timestamp = System.currentTimeMillis(),
            sender = sender
        )
        chatMessage.add(userMessage)
        _messagesFlow.value = chatMessage.toList()
        webSocketClient.send(chatId, text)
    }

    override fun observeMessages(chatId: Int): Flow<List<Message>> {
        activeChatId = chatId
        val chatMessage = messagesMap.getOrPut(chatId) { mutableListOf() }
        if (chatMessage.isEmpty()) {
            chatMessage.add(
                Message(
                    id = -1,
                    text = "Привет! Это тестовое сообщение!",
                    timestamp = System.currentTimeMillis() - 60000,
                    sender = User(id = 2, login = "Собеседник")
                )
            )
        }
        _messagesFlow.value = chatMessage.toList()
        return _messagesFlow.asStateFlow()
    }
}