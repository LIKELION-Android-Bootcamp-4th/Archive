package com.likelion.liontalk.features.chatroom

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatRoomViewModel(application: Application, private val roomId: Int) : ViewModel(){
    private val chatMessageDao = AppDatabase.create(application)
        .chatMessageDao()

    val messages : LiveData<List<ChatMessageEntity>>
        = chatMessageDao.getMessagesForRoom(roomId)

    // 메세지 전송
    fun sendMessage(sender: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val messageEntity = ChatMessageEntity(
                roomId = roomId,
                sender = sender,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            chatMessageDao.insert(messageEntity)
        }
    }
}