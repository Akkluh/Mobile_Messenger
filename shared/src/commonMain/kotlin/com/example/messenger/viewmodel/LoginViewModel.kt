package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.domain.model.User
import com.example.messenger.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    fun login(login: String, password: String) {
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
            _errorMessage.value = null
            val result = loginUseCase.execute(login, password)
            result.fold(
                onSuccess = { user -> _currentUser.value = user},
                onFailure = {exception -> _currentUser.value = null
                    _errorMessage.value = exception.localizedMessage}
            )
            _isLoading.value = false
        }
    }
}