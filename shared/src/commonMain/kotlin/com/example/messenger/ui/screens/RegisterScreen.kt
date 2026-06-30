package com.example.messenger.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.messenger.ui.theme.BackGround
import com.example.messenger.ui.theme.Orange
import androidx.compose.ui.unit.sp
import com.example.messenger.ui.theme.messengerTextFieldColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color

@Composable
fun RegisterScreen(
    onRegister: (String, String, String, String) -> Unit,
    onBack: () -> Unit,
    errorMessage: String?
) {
    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(32.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = Orange
            ) {
                Text(
                    text = "Messenger!",
                    modifier = Modifier.padding(
                        horizontal = 40.dp,
                        vertical = 14.dp
                    ),
                    color = BackGround,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(32.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = BackGround),
                border = BorderStroke(2.dp, Orange),
                elevation = CardDefaults.cardElevation(12.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it },
                        label = { Text("Login") },
                        colors = messengerTextFieldColors()
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        colors = messengerTextFieldColors()
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        colors = messengerTextFieldColors()
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = repeatPassword,
                        onValueChange = { repeatPassword = it },
                        label = { Text("Repeat password") },
                        colors = messengerTextFieldColors()
                    )
                }
            }
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = {onRegister(login, email, password, repeatPassword)},
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(Orange)) {
                Text(text = "Create account")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = onBack
            ) {
                Text(text = "Есть аккаунт? Авторизоваться", color = Orange)
            }
            if (errorMessage != null) {
                Spacer(
                    Modifier.height(20.dp)
                )
                Surface(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    color = Color(0xFFFFF0F0),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFD32F2F)
                    )
                ) {
                    Box(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage,
                            color = Color(0xFFD32F2F),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
