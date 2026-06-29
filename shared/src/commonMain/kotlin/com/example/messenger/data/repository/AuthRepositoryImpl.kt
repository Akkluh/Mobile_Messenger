package com.example.messenger.data.repository

import com.example.messenger.data.network.SoapClient
import com.example.messenger.data.network.XmlMockLoader
import com.example.messenger.data.network.XmlParser
import com.example.messenger.domain.repository.AuthRepository
import com.example.messenger.domain.model.User
import java.io.IOException

class AuthRepositoryImpl : AuthRepository {
    private val soapClient = SoapClient()
    private val xmlResponse = XmlMockLoader()
    private val parser = XmlParser()
    // private val isNetworkAvailable = false
    override suspend fun logIn(login: String, password: String): Result<User> {
        return try {
//            if (!isNetworkAvailable) {
//                throw IOException("Network is not available")
//            }
            val request = soapClient.buildLoginRequest(login, password)
            val response = xmlResponse.loadLoginResponse(request)
            val user = parser.parseUser(response)
            if (user != null) {
                Result.success(user)
            }
            else {
                Result.failure(Exception("Неверный логин или пароль"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Отсутствует подключение к сети. Проверьте интернет."))
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка сервера при обработке данных"))
        }
    }
    override suspend fun register(login: String, password: String): User? {
        return User(1, login)
    }
}