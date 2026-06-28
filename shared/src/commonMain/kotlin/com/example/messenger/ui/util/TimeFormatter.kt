package com.example.messenger.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timeFormat(timestamp: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}