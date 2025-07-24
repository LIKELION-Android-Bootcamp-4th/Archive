package com.likelion.liontalk.features.chatroom

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatRoomViewModelFactory(
    private val application : Application,
    private val roomId: Int) : ViewModelProvider.Factory {
    override  fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatRoomViewModel::class.java)) {
            return ChatRoomViewModel(application,roomId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}