package com.likelion.liontalk.features.chatroomlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likelion.liontalk.data.local.AppDatabase
import kotlinx.coroutines.launch

class ChatRoomListViewModel(application: Application) : ViewModel() {

    private val _state = MutableLiveData(ChatRoomListState())
    val state : LiveData<ChatRoomListState> = _state

    private val chatRoomDao = AppDatabase.create(application).chatRoomDao()

    init {
        loadChatRooms()
    }

    private fun loadChatRooms() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            try {
                chatRoomDao.getChatRooms().observeForever { rooms ->
                    _state.postValue(
                        ChatRoomListState(
                            isLoading = false,
                            chatRooms = rooms
                        )
                    )
                }
            } catch (e : Exception ) {
                _state.value = _state.value?.copy(isLoading = false, error = e.message)
            }
        }
    }
}