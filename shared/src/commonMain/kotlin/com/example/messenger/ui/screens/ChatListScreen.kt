package com.example.messenger.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.messenger.domain.model.Chat
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.messenger.ui.theme.*

@Composable
fun ChatListScreen(
    chats: List<Chat>,
    onChatClick: (Chat) -> Unit,
    errorMessage: String?,
    onRefresh: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().background(Orange).padding(20.dp),
            contentAlignment = Alignment.Center) {
            Text(text = "Messenger!",
                color = MaterialTheme.colorScheme.background,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold)
        }
        if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        color = Color(0xFFFFF0F0),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFD32F2F)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = errorMessage,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onRefresh,
                        colors = ButtonDefaults.buttonColors(Orange)
                    )
                    {
                        Text(text = "Обновить")
                    }
                }
            }
        }
        else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(chats) { chat ->
                    Card(onClick = {onChatClick(chat)},  modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(text = chat.title, style = MaterialTheme.typography.titleMedium, color = Orange)
                        }
                    }
                }
            }
        }
    }
}