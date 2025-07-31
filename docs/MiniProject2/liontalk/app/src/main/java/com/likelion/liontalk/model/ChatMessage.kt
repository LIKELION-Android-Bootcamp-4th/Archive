package com.likelion.liontalk.model

class ChatMessage(
    val id: String="",
    val roomId: String,
    val sender: ChatUser,
    val content: String,
    val type: String? = "text",
    val createdAt: Long
)