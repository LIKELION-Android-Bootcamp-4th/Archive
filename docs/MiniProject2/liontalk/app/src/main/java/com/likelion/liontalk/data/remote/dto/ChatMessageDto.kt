package com.likelion.liontalk.data.remote.dto

data class ChatMessageDto(
    val id: Int = 0,
    val roomId: Int,
    val sender: String,
    val content : String,
    val createdAt : Long
)