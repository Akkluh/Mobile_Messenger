package com.example.messenger.domain.usecase
import com.example.messenger.domain.model.User
import com.example.messenger.domain.repository.AuthRepository
class RegisterUseCase(private val repository: AuthRepository) {
    suspend fun execute(login: String,email: String, password: String): Result<User> {
        return repository.register(login, email, password)
    }
}