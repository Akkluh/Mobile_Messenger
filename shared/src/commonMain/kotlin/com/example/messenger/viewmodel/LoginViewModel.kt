package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.domain.model.User
import com.example.messenger.domain.usecase.LoginUseCase
import com.example.messenger.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase, private val registerUseCase: RegisterUseCase): ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    fun login(login: String, password: String) {
        _errorMessage.value = null
        if (login.isBlank() || password.isBlank()) {
            _errorMessage.value = "Поля не должны быть пустыми"
            return
        }
        if (login.length < 4 || password.length < 4) {
            _errorMessage.value = "Пароль или логин слишком короткий, минимум 4 символа"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase.execute(login, password)
            result.fold(
                onSuccess = { user -> _currentUser.value = user},
                onFailure = {exception -> _currentUser.value = null
                    _errorMessage.value = exception.localizedMessage}
            )
            _isLoading.value = false
        }
    }
    fun register(login: String, email: String, password: String, repeatPassword: String) {
        _errorMessage.value = null
        if (login.isBlank() || email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            _errorMessage.value = "Зполните все поля"
            return
        }
        if (login.length < 4 || password.length < 4) {
            _errorMessage.value = "Пароль или логин слишком короткий, минимум 4 символа"
            return
        }
        if (!email.contains("@")) {
            _errorMessage.value = "Некорректный e-mail"
            return
        }
        if (password != repeatPassword) {
            _errorMessage.value = "Пароли не совпадают"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val result = registerUseCase.execute(login, email, password)
            result.fold(
                onSuccess = { _currentUser.value = it},
                onFailure = {_errorMessage.value = it.localizedMessage}
            )
            _isLoading.value = false
        }
    }
}