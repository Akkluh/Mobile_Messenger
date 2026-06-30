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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun RegisterScreen(
    onRegister: (String, String, String, String) -> Unit,
    onBack: () -> Unit,
    errorMessage: String?,
    onCleanError: () -> Unit,
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
                        onValueChange = { login = it
                            onCleanError()},
                        label = { Text("Login") },
                        colors = messengerTextFieldColors()
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it
                                        onCleanError()},
                        label = { Text("Email") },
                        colors = messengerTextFieldColors()
                    )
                    Spacer(Modifier.height(16.dp))
                    var passwordVisible by remember { mutableStateOf(false) }
                    OutlinedTextField(value = password, onValueChange = { password = it
                                                                        onCleanError()},
                        label = {Text("Password")}, colors = messengerTextFieldColors(),
                        visualTransformation =  if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    tint = Orange)
                            }
                        })
                    Spacer(Modifier.height(16.dp))
                    var repeatPasswordVisible by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = repeatPassword,
                        onValueChange = { repeatPassword = it
                                        onCleanError()},
                        label = { Text("Repeat password") },
                        colors = messengerTextFieldColors(),
                        visualTransformation = if (repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { repeatPasswordVisible = !repeatPasswordVisible }) {
                                Icon(imageVector = if (repeatPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (repeatPasswordVisible) "Hide repeat password" else "Show repeat password",
                                    tint = Orange)
                            }
                        }
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
