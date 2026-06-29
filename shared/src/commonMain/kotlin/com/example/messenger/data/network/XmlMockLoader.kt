package com.example.messenger.data.network

import messenger.shared.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.io.IOException

class XmlMockLoader {
    @OptIn(ExperimentalResourceApi::class)
    suspend fun loadXmlResponse(fileName: String): String {
        return try {
            Res.readBytes("files/$fileName").decodeToString()
        } catch (e: Exception) {
            throw IOException("Ошибка при чтении XML-заглушки", e)
        }
    }
}