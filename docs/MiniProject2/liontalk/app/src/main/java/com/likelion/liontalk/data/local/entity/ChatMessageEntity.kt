package com.likelion.liontalk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.likelion.liontalk.model.ChatUser

@Entity(tableName = "chat_message")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val roomId: Int,        // 방 ID
    val sender: ChatUser,     // 보낸 사람
    val content: String,    // 보낸 메세지
    val createdAt: Long     // 시간
)
