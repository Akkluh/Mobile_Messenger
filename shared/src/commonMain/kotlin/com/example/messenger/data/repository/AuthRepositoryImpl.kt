package com.example.messenger.data.repository
import com.example.messenger.domain.repository.AuthRepository
import com.example.messenger.domain.model.User

class AuthRepositoryImpl : AuthRepository {
    override suspend fun logIn(login: String, password: String): User? {
        if (login == "admin" && password == "admin") {
            return User(1, "admin")
        }
        return null
    }
    override suspend fun register(login: String, password: String): User? {
        return User(1, login)
    }
}