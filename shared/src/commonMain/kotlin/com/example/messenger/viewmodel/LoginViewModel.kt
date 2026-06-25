package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import com.example.messenger.domain.model.User
import com.example.messenger.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel(private val loginUseCase: LoginUseCase) {
    val currentUser = MutableStateFlow<User?>(null)
    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow<String?>(null)
    suspend fun login(login: String, password: String) {
        isLoading.value = true
        val user = loginUseCase.execute(login, password)
        currentUser.value = user
        if (user == null) {
            errorMessage.value = "Error while logging in"
        }
        isLoading.value = false
    }
}