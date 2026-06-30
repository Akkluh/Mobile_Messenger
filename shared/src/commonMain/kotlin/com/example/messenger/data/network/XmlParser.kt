package com.example.messenger.data.network

import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.model.User

class XmlParser {
    fun parseUser(response: String): User? {
        if (response.contains("<mes:Error>") || !response.contains("<mes:User>")) {
            return null
        }
        return try {
            val id = response.substringAfter("<mes:Id>").substringBefore("</mes:Id>").toInt()
            val login = response.substringAfter("<mes:Login>").substringBefore("</mes:Login>")
            User(id, login)
        } catch (e: Exception) {
            null
        }
    }
    fun parseChatList(response: String): List<Chat> {
        if (response.contains("<mes:Error>") || !response.contains("<mes:ChatList>")) {
            return emptyList()
        }
        val chats = mutableListOf<Chat>()
        val rawChunks = response.split("<mes:Chat>")
        for (i in 1 until rawChunks.size) {
            val chunk = rawChunks[i]
            try {
                val id = chunk.substringAfter("<mes:Id>").substringBefore("</mes:Id>").toInt()
                val title = chunk.substringAfter("<mes:Title>").substringBefore("</mes:Title>")
                chats.add(Chat(id, title, participants = emptyList(), messages = emptyList()))
            } catch (e: Exception) {
                continue
            }
        }
        return chats
    }
}