package com.likelion.liontalk.model

class ChatMessage(
    val id: Int = 0,
    val roomId: Int,
    val sender: ChatUser,
    val content: String,
    val type: String? = "text",
    val createdAt: Long
)