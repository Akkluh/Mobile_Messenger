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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.input.key.*
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime

@Composable
fun ChatScreen(chat: Chat, onBack: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Orange)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = chat.title, color = Orange, style = MaterialTheme.typography.headlineSmall)
        }
        HorizontalDivider()
        Box(modifier = Modifier.weight(1f).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Сообщений пока нет")
        }
        Row(modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = messageText, onValueChange = { messageText = it }, colors = messengerTextFieldColors(),modifier = Modifier.weight(1f), placeholder = {Text("Сообщение...")})
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = {}, enabled = messageText.isNotBlank()) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send   , contentDescription = "Send", tint = Orange)
            }
        }
    }
}