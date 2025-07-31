package com.likelion.liontalk.model

data class ChatRoom(val id: String = "",
                    val title : String,
                    val owner : ChatUser,
                    val users : List<ChatUser> = emptyList(),
                    val unReadCount:Int = 0,
                    val lastReadMessageId:String = "",
                    val isLocked : Boolean = false,
                    val createdAt: Long)