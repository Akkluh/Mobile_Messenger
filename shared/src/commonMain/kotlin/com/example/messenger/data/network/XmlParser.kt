package com.example.messenger.data.network

import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.model.User

class XmlParser {
    fun parseUser(response: String): User? {
        if (response.contains("<Error>") || !response.contains("<User>")) {
            return null
        }
        return try {
            val id = response.substringAfter("<Id>").substringBefore("</Id>").toInt()
            val login = response.substringAfter("<Login>").substringBefore("</Login>")
            User(id, login)
        } catch (e: Exception) {
            println("ОШИБКА: ${e.message}")
            null
        }
    }
    fun parseChatList(response: String): List<Chat> {
        if (response.contains("<Error>") || !response.contains("<ChatList>")) {
            return emptyList()
        }
        val chats = mutableListOf<Chat>()
        val rawChunks = response.split("<Chat>")
        for (i in 1 until rawChunks.size) {
            val chunk = rawChunks[i]
            try {
                val id = chunk.substringAfter("<Id>").substringBefore("</Id>").toInt()
                val title = chunk.substringAfter("<Title>").substringBefore("</Title>")
                chats.add(Chat(id, title, participants = emptyList(), messages = emptyList()))
            } catch (e: Exception) {
                continue
            }
        }
        return chats
    }
}