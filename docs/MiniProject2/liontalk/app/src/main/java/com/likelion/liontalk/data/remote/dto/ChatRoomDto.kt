package com.likelion.liontalk.data.remote.dto

data class ChatRoomDto(
    val id: Int,
    val title: String,
    val owner: String,
    val users: List<String>,
    val createdAt: Long
)