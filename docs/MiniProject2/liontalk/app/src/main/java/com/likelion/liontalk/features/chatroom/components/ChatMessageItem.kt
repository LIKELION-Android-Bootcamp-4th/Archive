package com.likelion.liontalk.features.chatroom.components

import androidx.compose.runtime.Composable
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.model.ChatMessage

@Composable
fun ChatMessageItem(message: ChatMessage, isMe : Boolean) {
    when {
        message.type == "system" -> SystemMessageItem(message.content)
        isMe -> MyMessageItem(message)
        else -> OtherMessageItem(message)
    }
}