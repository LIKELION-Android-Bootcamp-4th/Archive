package com.likelion.liontalk.features.chatroom.components

import androidx.compose.runtime.Composable
import com.likelion.liontalk.data.local.entity.ChatMessageEntity

@Composable
fun ChatMessageItem(message: ChatMessageEntity, isMe : Boolean) {
    when {
        isMe -> MyMessageItem(message)
        else -> OtherMessageItem(message)
    }
}