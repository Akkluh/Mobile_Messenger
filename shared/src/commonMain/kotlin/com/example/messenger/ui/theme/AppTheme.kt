package com.example.messenger.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
private val Scheme = lightColorScheme(
    primary = Orange,
    background = BackGround,
    surface = BackGround,
    onPrimary = BackGround,
    onBackground = TextDark,
)
@Composable
fun MessengerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Scheme,
        content = content
    )
}