package com.likelion.liontalk.features.chatroomlist

import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.entity.ChatRoomEntity

data class ChatRoomListState(
    val isLoading : Boolean = false,
    val chatRooms : List<ChatRoomEntity> = emptyList(),
    val error : String? = null
) {
}