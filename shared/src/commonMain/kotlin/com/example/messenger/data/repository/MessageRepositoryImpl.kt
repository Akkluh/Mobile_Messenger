package com.example.messenger.data.repository

import com.example.messenger.data.network.WebSocketClient
import com.example.messenger.domain.model.Message
import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.MessageRepository
import com.example.messenger.database.MessengerDatabase
import com.example.messenger.database.MessengerDatabaseQueries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
class MessageRepositoryImpl(
    private val driverFactory: DatabaseDriverFactory
) : MessageRepository {

    private val socket = WebSocketClient()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val database = MessengerDatabase(driverFactory.createDriver())
    private val dbQueries: MessengerDatabaseQueries = database.messengerDatabaseQueries
    private val messagesMap = mutableMapOf<Int, MutableList<Message>>()
    private val flows = mutableMapOf<Int, MutableStateFlow<List<Message>>>()

    init {
        socket.connect()
        scope.launch {
            socket.incomingMessages.collect {
                val messageId = System.currentTimeMillis()
                dbQueries.insertMessage(
                    id = messageId,
                    chat_id = it.chatId.toLong(),
                    sender_id = 114L,
                    sender_name = "Собеседник",
                    text = it.text,
                    timestamp = System.currentTimeMillis()
                )
                val history = messagesMap.getOrPut(it.chatId) { mutableListOf() }
                history.add(
                    Message(
                        id = messageId.toInt(),
                        text = it.text,
                        timestamp = System.currentTimeMillis(),
                        sender = User(114, "Собеседник")
                    )
                )
                flows[it.chatId]?.value = history.toList()
            }
        }
    }

    override suspend fun send(chatId: Int, text: String, sender: User) {
        val messageId = System.currentTimeMillis()
        dbQueries.insertMessage(
            id = messageId,
            chat_id = chatId.toLong(),
            sender_id = sender.id.toLong(),
            text = text,
            sender_name = sender.login,
            timestamp = System.currentTimeMillis()
        )
        val history = messagesMap.getOrPut(chatId) { mutableListOf() }
        history.add(
            Message(
                id = messageId.toInt(),
                text = text,
                timestamp = System.currentTimeMillis(),
                sender = sender
            )
        )
        flows[chatId]?.value = history.toList()
        socket.send(chatId, text)
    }

    override fun observeMessages(chatId: Int): Flow<List<Message>> {
        val history = messagesMap.getOrPut(chatId) {
            val dbList = dbQueries.loadMessages(chatId.toLong()).executeAsList()
            if (dbList.isEmpty()) {
                mutableListOf(
                    Message(
                        id = 1,
                        text = "Привет! Это тест",
                        timestamp = System.currentTimeMillis(),
                        sender = User(2, "Собеседник")
                    )
                )
            } else {
                dbList.map { dbMsg ->
                    Message(
                        id = dbMsg.id.toInt(),
                        text = dbMsg.text,
                        timestamp = dbMsg.timestamp,
                        sender = User(id = dbMsg.sender_id.toInt(), login = dbMsg.sender_name)
                    )
                }.toMutableList()
            }
        }

        return flows.getOrPut(chatId) {
            MutableStateFlow(history.toList())
        }
    }
}