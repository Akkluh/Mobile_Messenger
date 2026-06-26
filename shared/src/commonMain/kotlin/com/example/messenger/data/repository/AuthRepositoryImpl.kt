package com.example.messenger.data.repository
import com.example.messenger.data.network.SoapClient
import com.example.messenger.data.network.XmlMockLoader
import com.example.messenger.data.network.XmlParser
import com.example.messenger.domain.repository.AuthRepository
import com.example.messenger.domain.model.User

class AuthRepositoryImpl : AuthRepository {
    private val soapClient = SoapClient()
    private val xmlResponse = XmlMockLoader()
    private val parser = XmlParser()
    override suspend fun logIn(login: String, password: String): User? {
        val request = soapClient.buildLoginRequest(login, password)
        val response = xmlResponse.loadLoginResponse(request)
        val user = parser.parseUser(response)
        return user
    }
    override suspend fun register(login: String, password: String): User? {
        return User(1, login)
    }
}