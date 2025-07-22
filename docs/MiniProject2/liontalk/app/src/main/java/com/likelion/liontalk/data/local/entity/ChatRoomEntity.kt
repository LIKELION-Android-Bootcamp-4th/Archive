package com.likelion.liontalk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_room")
data class ChatRoomEntity(
    @PrimaryKey val id: Int = 0,
    val title : String,
    val owner : String,
    val users : List<String> = emptyList(),
    val createdAt: Long
)
