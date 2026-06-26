package com.example.messenger

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.messenger.data.repository.AuthRepositoryImpl
import com.example.messenger.domain.usecase.LoginUseCase
import com.example.messenger.ui.screens.LoginScreen
import com.example.messenger.viewmodel.LoginViewModel
import org.jetbrains.compose.resources.painterResource
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.messenger.data.repository.ChatRepositoryImpl
import com.example.messenger.domain.usecase.LoadChatListUseCase
import messenger.shared.generated.resources.Res
import messenger.shared.generated.resources.compose_multiplatform
import com.example.messenger.ui.screens.ChatListScreen
import com.example.messenger.viewmodel.ChatViewModel
import com.example.messenger.domain.model.Chat

@Composable
@Preview
fun App() {
    val viewModel = remember { LoginViewModel(
        LoginUseCase(AuthRepositoryImpl()),
    ) }
    val chatViewModel = remember { ChatViewModel(LoadChatListUseCase(ChatRepositoryImpl())) }
    val scope = rememberCoroutineScope()
    val currenUser by viewModel.currentUser.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val chatList by chatViewModel.chatList.collectAsState()
    val selectedChat by remember {mutableStateOf<Chat?>(null)}
    LaunchedEffect(currenUser) {
        if (currenUser != null) {
            chatViewModel.loadChatList()
        }
    }
    MaterialTheme {
        if (currenUser == null) {
            LoginScreen(onLogin = { login, password -> scope.launch {viewModel.login(login, password)}}, errorMessage = errorMessage)
        }
        else{
            ChatListScreen(chats = chatList, onChatClick = {println(it)})
        }
    }
}