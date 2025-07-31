package com.likelion.liontalk.data.remote.dto

import com.likelion.liontalk.model.ChatUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomRequestDto(
    val title: String,
    val owner: ChatUser,
    val users: List<ChatUser> = emptyList<ChatUser>(),

    @SerialName("is_locked")
    val isLocked : Boolean,
    @SerialName("created_at")
    val createdAt: Long
)