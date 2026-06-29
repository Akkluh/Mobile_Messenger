package com.example.messenger.data.repository

import com.example.messenger.data.network.SoapClient
import com.example.messenger.data.network.XmlMockLoader
import com.example.messenger.data.network.XmlParser
import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.repository.ChatRepository
import java.io.IOException

class ChatRepositoryImpl: ChatRepository {
    private val soapClient = SoapClient()
    private val xmlMockLoader = XmlMockLoader()
    private val parser = XmlParser()
    private var isNetworkAvailable = true
    override suspend fun getChatList(): List<Chat> {
        return try {
            if (!isNetworkAvailable) {
                throw Exception("Network not available")
            }
            val request = soapClient.buildChatsRequest(userId = 1)
            val response = xmlMockLoader.loadXmlResponse("chat_response.xml")
            val chatList = parser.parseChatList(response)
            chatList
        } catch (e: IOException) {
        throw IOException("Отсутствует подключение к сети", e)
        } catch (e: Exception) {
            println("ChatRepository: Ошибка закрузки списка чатов: ${e.localizedMessage}")
            emptyList()
        }
    }
}