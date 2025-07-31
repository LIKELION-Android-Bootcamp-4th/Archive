package com.likelion.liontalk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.likelion.liontalk.model.ChatUser

@Entity(tableName = "chat_room")
data class ChatRoomEntity(
    @PrimaryKey val id: String="",
    val title : String,
    val owner : ChatUser,
    val users : List<ChatUser> = emptyList(),
    val unReadCount:Int = 0,
    val lastReadMessageId:String="",
    val lastReadMessageTimestamp: Long = 0L,
    val isLocked : Boolean = false,
    val createdAt: Long
)
