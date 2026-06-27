package com.example.messenger.ui.theme

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Orange = Color(0xFFFF6A00)
val BackGround = Color(0xFFFFFBF5)
val TextDark = Color(0xFF1A1A1A)
val Card = Color(0xFFFFFBF5)

@Composable
fun messengerTextFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Orange,
        focusedLabelColor = Orange,
        cursorColor = Orange,
        unfocusedBorderColor = Orange.copy(alpha = 0.5f),
        focusedContainerColor = BackGround,
        unfocusedContainerColor = BackGround,
    )
}