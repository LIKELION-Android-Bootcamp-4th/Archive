package com.likelion.liontalk.data.remote.dto

import com.likelion.liontalk.model.ChatUser

data class ChatMessageDto(
    val id: String="",
    val roomId: String="",
    val sender: ChatUser = ChatUser("",""),
    val content : String ="",
    val createdAt : Long = 0L
)