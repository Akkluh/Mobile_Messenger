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
    override suspend fun logIn(login: String, password: String): Result<User> {
        return try {
            val request = soapClient.buildLoginRequest(login, password)
            // Здесь должен быть сетевой вызов, но пока вместо сервера мок
            val response = xmlResponse.loadXmlResponse("login_response.xml")
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
    override suspend fun register(login: String, email: String, password: String): Result<User> {
        return try {
            val request = soapClient.buildRegisterRequest(login, email, password)
            val response = xmlResponse.loadXmlResponse("register_response.xml")
            val user = parser.parseUser(response)
            if (user != null) {
                Result.success(user)
            }
            else {
                Result.failure(Exception("Ошибка регистрации"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Нет подключения"))
        }
    }
}