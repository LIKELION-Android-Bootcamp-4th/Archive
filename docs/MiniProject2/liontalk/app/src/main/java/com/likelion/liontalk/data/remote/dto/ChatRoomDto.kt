package com.likelion.liontalk.data.remote.dto

import com.likelion.liontalk.model.ChatUser

data class ChatRoomDto(
    val id: Int,
    val title: String,
    val owner: ChatUser,
    val users: List<ChatUser>,
    val isLocked : Boolean,
    val createdAt: Long
)