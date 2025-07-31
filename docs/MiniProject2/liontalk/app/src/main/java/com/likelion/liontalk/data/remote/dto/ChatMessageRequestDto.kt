package com.likelion.liontalk.data.remote.dto

import com.likelion.liontalk.model.ChatUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageRequestDto(
    @SerialName("room_id")
    val roomId: Int,
    val sender: ChatUser,
    val content : String,
    @SerialName("created_at")
    val createdAt : Long
)