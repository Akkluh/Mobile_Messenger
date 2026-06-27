package com.example.messenger.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.messenger.domain.model.Chat
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import com.example.messenger.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items

@Composable
fun ChatScreen(chat: Chat, onBack: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<String>()) }
    Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Orange)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = chat.title, color = Orange, style = MaterialTheme.typography.headlineSmall)
        }
        HorizontalDivider()
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp)) {
            if (messages.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Сообщений пока нет")
                    }
                }
            }
            else {
                items(messages){
                    message -> Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Card(modifier = Modifier.padding(bottom = 8.dp), colors = CardDefaults.cardColors(containerColor = Orange)) {
                        Text(text = message, modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.background)
                    }
                }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = messageText, onValueChange = { messageText = it }, colors = messengerTextFieldColors(),modifier = Modifier.weight(1f), placeholder = {Text("Сообщение...")})
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = {
                if (messageText.isNotBlank()) {
                    messages = messages + messageText
                    messageText = ""
                }}, enabled = messageText.isNotBlank()) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send   , contentDescription = "Send", tint = Orange)
            }
        }
    }
}