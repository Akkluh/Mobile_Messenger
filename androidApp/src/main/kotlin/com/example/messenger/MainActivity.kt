package com.example.messenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.messenger.data.repository.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val driverFactory = DatabaseDriverFactory(this)
        setContent {
            App(driverFactory)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val previewDriverFactory = remember { DatabaseDriverFactory(context) }
    App(previewDriverFactory)
}