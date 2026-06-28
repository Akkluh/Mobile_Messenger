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
import com.example.messenger.viewmodel.MessageViewModel
import com.example.messenger.ui.util.timeFormat

@Composable
fun ChatScreen(chat: Chat, onBack: () -> Unit, messagesViewModel: MessageViewModel) {
    var messageText by remember { mutableStateOf("") }
    val messages = messagesViewModel.messages.collectAsState()
    val isLoading = messagesViewModel.loading.collectAsState()
    val loaded = messagesViewModel.loaded.collectAsState()
    LaunchedEffect(chat.id) {
        messagesViewModel.loadMessages(chat.id)
    }
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
            if (!loaded.value || isLoading.value) {
                item{
                    Box(modifier = Modifier.fillParentMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Orange)
                    }
                }
            }
            else if(messages.value.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "Сообщений пока нет")
                    }
                }
            }
            else {
                items(messages.value){
                    message -> Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Card(modifier = Modifier.padding(bottom = 8.dp), colors = CardDefaults.cardColors(containerColor = Orange)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = message.sender.login, color = MaterialTheme.colorScheme.background.copy(alpha = 0.7f), style = MaterialTheme.typography.labelMedium)
                                Text(text = message.text, color = MaterialTheme.colorScheme.background)
                                Text(text = timeFormat(message.timestamp), color = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                                    style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.End))
                            }

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
                    messagesViewModel.sendMessage(chat.id, messageText)
                    messageText = ""
                }}, enabled = messageText.isNotBlank()) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send   , contentDescription = "Send", tint = Orange)
            }
        }
    }
}