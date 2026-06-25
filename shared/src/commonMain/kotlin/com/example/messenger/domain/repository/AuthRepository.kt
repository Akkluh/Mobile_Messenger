package com.example.messenger.domain.repository

import com.example.messenger.domain.model.User

interface AuthRepository {
    suspend fun logIn(login: String, password: String): User?
    suspend fun register(login: String, password: String): User?
}