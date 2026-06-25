package com.example.messenger.domain.usecase
import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend fun execute(login: String, password: String): User? {
        return repository.logIn(login, password)
    }
}