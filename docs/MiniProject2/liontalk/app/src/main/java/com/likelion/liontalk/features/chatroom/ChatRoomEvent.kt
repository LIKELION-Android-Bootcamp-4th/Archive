package com.likelion.liontalk.features.chatroom

sealed class ChatRoomEvent {
    data class TypingStarted(val sender: String) : ChatRoomEvent()
    object TypingStopped : ChatRoomEvent()
    data class ChatRoomEnter(val name:String):ChatRoomEvent()
    data class ChatRoomLeave(val name:String):ChatRoomEvent()
    object ScrollToBottom : ChatRoomEvent()
    object ClearInput : ChatRoomEvent()
}