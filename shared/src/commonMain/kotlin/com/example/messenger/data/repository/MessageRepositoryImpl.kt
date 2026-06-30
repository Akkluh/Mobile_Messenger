package com.example.messenger.data.repository

import com.example.messenger.data.network.WebSocketClient
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessageRepositoryImpl :
    MessageRepository {
    private val socket =
        WebSocketClient()
    private val scope =
        CoroutineScope(
            SupervisorJob()
                    + Dispatchers.Default
        )
    private val messagesMap =
        mutableMapOf<
                Int,
                MutableList<Message>
                >()
    private val flows =
        mutableMapOf<
                Int,
                MutableStateFlow<List<Message>>
                >()
    init {
        socket.connect()
        scope.launch {
            socket.incomingMessages.collect {
                val history =
                    messagesMap.getOrPut(
                        it.chatId
                    ) {
                        mutableListOf()
                    }
                history.add(
                    Message(
                        id =
                            history.size + 1,

                        text =
                            it.text,

                        timestamp =
                            System.currentTimeMillis(),

                        sender =
                            User(
                                114,
                                "Собеседник"
                            )
                    )
                )
                flows[it.chatId]
                    ?.value =
                    history.toList()
            }
        }
    }
    override suspend fun send(
        chatId: Int,
        text: String,
        sender: User
    ) {
        val history =
            messagesMap.getOrPut(
                chatId
            ) {
                mutableListOf()
            }
        history.add(
            Message(
                id =
                    history.size + 1,

                text =
                    text,

                timestamp =
                    System.currentTimeMillis(),

                sender =
                    sender
            )
        )
        flows[chatId]
            ?.value =
            history.toList()
        socket.send(
            chatId,
            text
        )
    }
    override fun observeMessages(
        chatId: Int
    ): Flow<List<Message>> {
        val history =
            messagesMap.getOrPut(
                chatId
            ) {
                mutableListOf(
                    Message(
                        id = 1,
                        text =
                            "Привет! Это тест",
                        timestamp =
                            System.currentTimeMillis(),
                        sender =
                            User(
                                2,
                                "Собеседник"
                            )
                    )
                )
            }
        return flows.getOrPut(
            chatId
        ) {
            MutableStateFlow(
                history.toList()
            )
        }
    }
}