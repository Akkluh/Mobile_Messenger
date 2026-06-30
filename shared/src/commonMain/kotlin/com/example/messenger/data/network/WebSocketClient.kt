package com.example.messenger.data.network

import com.example.messenger.domain.model.SocketMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class WebSocketClient {
    private var isConnected = false
    private val _incomingMessages = MutableSharedFlow<SocketMessage>()
    val incomingMessages: SharedFlow<SocketMessage> = _incomingMessages.asSharedFlow()
    fun connect() {
        isConnected = true
        println("Connected to WebSocket!")
    }
    fun disconnect() {
        isConnected = false
        println("Disconnected from WebSocket!")
    }
    suspend fun send(chatId: Int, messageText: String) {
        if (!isConnected) return
        // симулирование задержки от сервера
        delay(1500)
        // иммуляция ответа сервера
        _incomingMessages.emit(SocketMessage(chatId, "Получено сообщение: \"$messageText\""))
    }
}