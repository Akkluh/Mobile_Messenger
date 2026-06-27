package com.example.messenger

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.messenger.data.repository.AuthRepositoryImpl
import com.example.messenger.domain.usecase.LoginUseCase
import com.example.messenger.ui.screens.LoginScreen
import com.example.messenger.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.messenger.data.repository.ChatRepositoryImpl
import com.example.messenger.data.repository.MessageRepositoryImpl
import com.example.messenger.domain.usecase.LoadChatListUseCase
import com.example.messenger.ui.screens.ChatListScreen
import com.example.messenger.viewmodel.ChatViewModel
import com.example.messenger.domain.model.Chat
import com.example.messenger.domain.usecase.LoadMessagesUseCase
import com.example.messenger.domain.usecase.SendMessageUseCase
import com.example.messenger.ui.screens.ChatScreen
import com.example.messenger.viewmodel.MessageViewModel

@Composable
@Preview
fun App() {
    val viewModel = remember { LoginViewModel(
        LoginUseCase(AuthRepositoryImpl()),
    ) }
    val chatViewModel = remember { ChatViewModel(LoadChatListUseCase(ChatRepositoryImpl())) }
    val messageRepository = remember { MessageRepositoryImpl() }
    val messageViewModel = remember {
        MessageViewModel(
            SendMessageUseCase(messageRepository),
            LoadMessagesUseCase(messageRepository)
        )
    }
    val scope = rememberCoroutineScope()
    val currenUser by viewModel.currentUser.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val chatList by chatViewModel.chatList.collectAsState()
    var selectedChat by remember {mutableStateOf<Chat?>(null)}
    LaunchedEffect(currenUser) {
        if (currenUser != null) {
            chatViewModel.loadChatList()
        }
    }
    MaterialTheme {
        if (currenUser == null) {
            LoginScreen(onLogin = { login, password -> scope.launch {viewModel.login(login, password)}}, errorMessage = errorMessage)
        }
        else if (selectedChat == null) {
            ChatListScreen(chats = chatList, onChatClick = {chat -> selectedChat = chat})
        }
        else {
            selectedChat?.let {chat ->
                val currentChatViewModel = remember(chat.id) {
                    MessageViewModel(SendMessageUseCase(messageRepository), LoadMessagesUseCase(messageRepository))
                }
                ChatScreen(chat = chat, onBack = {selectedChat = null}, currentChatViewModel)}
        }
    }
}