package com.example.messenger.data.network

import com.example.messenger.domain.model.User

class XmlParser {
    fun parseUser(response: String): User? {
        if (response.contains("<Error>")) {
            return null
        }
        val id = response.substringAfter("<id>").substringBefore("</id>").toInt()
        val login = response.substringAfter("<login>").substringBefore("</login>")
        return User(id, login)
    }
}