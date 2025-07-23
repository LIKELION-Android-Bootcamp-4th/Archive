package com.likelion.liontalk.features.chatroomlist

import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.model.ChatRoom

enum class ChatRoomTab {
    JOINED, NOT_JOINED
}

data class ChatRoomListState(
    val isLoading : Boolean = false,
//    val chatRooms : List<ChatRoomEntity> = emptyList(),
    val chatRooms : List<ChatRoom> = emptyList(),
    val joinedRooms : List<ChatRoom> = emptyList(),     // 내가 참가한 채팅방 목록
    val notJoinedRooms : List<ChatRoom> = emptyList(),  // 참가하지 않은 채팅방 목록
    val currentTab: ChatRoomTab = ChatRoomTab.JOINED,
    val error : String? = null
)