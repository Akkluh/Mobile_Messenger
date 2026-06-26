package com.example.messenger.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.messenger.domain.model.Chat
import androidx.compose.foundation.lazy.items

@Composable
fun ChatListScreen(
    chats: List<Chat>,
    onChatClick: (Chat) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(chats) { chat -> Card(onClick = {onChatClick(chat)},  modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)){
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = chat.title)
            }
        } }
    }
}