package com.example.messenger.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import com.example.messenger.domain.model.User
import com.example.messenger.viewmodel.MessageViewModel
import com.example.messenger.ui.util.timeFormat
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ChatScreen(chat: Chat, onBack: () -> Unit, messagesViewModel: MessageViewModel, currentUser: User) {
    var messageText by remember { mutableStateOf("") }
    val messages = messagesViewModel.messages.collectAsState()
    val isLoading = messagesViewModel.loading.collectAsState()
    val loaded = messagesViewModel.loaded.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var chatErrorText by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(chat.id) {
        messagesViewModel.loadMessages(chat.id)
    }
    LaunchedEffect(messages.value.size) {
        if (messages.value.isNotEmpty()) {
            listState.animateScrollToItem(messages.value.lastIndex)
        }
    }
    LaunchedEffect(chatErrorText) {
        if (chatErrorText != null) {
            delay(3000)
            chatErrorText = null
        }
    }
    Box(modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding())
        {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Orange)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = chat.title, color = Orange, style = MaterialTheme.typography.headlineSmall)
            }
            HorizontalDivider()
            if (chatErrorText != null) {
                Surface(
                    color = Color(0xFFFFF0F0),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, color = Color(0xFFD32F2F))
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp, 12.dp),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = chatErrorText!!,
                            color = Color(0xFFD32F2F),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp), state = listState) {
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
                            message ->
                        val isMine = message.sender.id == currentUser.id
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start) {
                            Card(modifier = Modifier.padding(bottom = 8.dp), colors = CardDefaults.cardColors(containerColor =
                                if (isMine) Orange else MaterialTheme.colorScheme.surfaceVariant)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(text = message.sender.login, color =
                                        if (isMine) MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
                                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                        style = MaterialTheme.typography.labelMedium)
                                    Text(text = message.text, color =
                                        if (isMine) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface)
                                    Text(text = timeFormat(message.timestamp), color =
                                        if (isMine) MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
                                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                        style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.End))
                                }

                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().imePadding().padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = messageText,
                    onValueChange = { messageText = it },
                    colors = messengerTextFieldColors(),
                    modifier = Modifier.weight(1f),
                    placeholder = {Text("Сообщение...")})
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(onClick = {
                    if (messageText.isNotBlank()) {
                        messagesViewModel.sendMessage(chat.id, messageText)
                        messageText = ""
                    }}, enabled = messageText.isNotBlank()) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Orange)
                }
            }
        }
    }
}