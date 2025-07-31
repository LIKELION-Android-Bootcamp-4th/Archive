package com.likelion.liontalk.data.remote.dto

import com.likelion.liontalk.model.ChatUser

data class ChatRoomDto(
    val id: String="",
    val title: String="",
    val owner: ChatUser = ChatUser("",""),
    val users: List<ChatUser> = emptyList(),
    val isLocked : Boolean = false,
    val createdAt: Long = 0L
)