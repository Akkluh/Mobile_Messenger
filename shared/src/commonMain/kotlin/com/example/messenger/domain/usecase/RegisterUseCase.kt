package com.example.messenger.domain.usecase
import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.AuthRepository
class RegisterUseCase(private val repository: AuthRepository) {
    suspend fun register(login: String, password: String): User? {
        return repository.register(login, password)
    }
}