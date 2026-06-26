package com.example.messenger.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.example.messenger.domain.model.Chat
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.unit.sp
import com.example.messenger.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun ChatScreen(chat: Chat, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Orange)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = chat.title, color = Orange, style = MaterialTheme.typography.headlineSmall)
        }
        HorizontalDivider()
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Сообщений пока нет")
        }
    }
}