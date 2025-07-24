package com.likelion.liontalk.features.chatroomlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import com.likelion.liontalk.data.remote.mqtt.MqttClient
import com.likelion.liontalk.data.repository.ChatMessageRepository
import com.likelion.liontalk.data.repository.ChatRoomRepository
import com.likelion.liontalk.data.repository.UserPreferenceRepository
import com.likelion.liontalk.model.ChatRoomMapper.toDto
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatRoomListViewModel(application: Application) : ViewModel() {

//    private val _state = MutableLiveData(ChatRoomListState())
//val state : LiveData<ChatRoomListState> = _state
    private val _state = MutableStateFlow(ChatRoomListState())
    val state : StateFlow<ChatRoomListState> = _state.asStateFlow()

    private val chatRoomRepository = ChatRoomRepository(application.applicationContext)
    private val chatMessageRepository = ChatMessageRepository(application.applicationContext)

    private val userPreferenceRepository = UserPreferenceRepository.getInstance()
    val me : ChatUser get() = userPreferenceRepository.requireMe()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                withContext(Dispatchers.IO) {
                    subscribeToMqttTopics()
                }

                withContext(Dispatchers.IO) {
                    chatMessageRepository.syncAllMessagesFromServer()
                }

                withContext(Dispatchers.IO) {
                    chatRoomRepository.syncFromServer()
                }

                chatRoomRepository.getChatRoomsFlow().collect { rooms ->

                    val joined = rooms.filter {it.users.any {p-> p.name == me.name}}
                    val notJoined = rooms.filter { it.users.none {p-> p.name == me.name} }
                    _state.value = _state.value.copy(
                        isLoading = false,
                        chatRooms = rooms,
                        joinedRooms = joined,
                        notJoinedRooms = notJoined
                    )
                }

            } catch (e : Exception ) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
    fun createChatRoom(title: String ){
        Log.d("ChatRoomListViewModel",title)
        viewModelScope.launch {
            try {

                Log.d("ChatRoomListViewModel",me.toString())

                val room = ChatRoomEntity(
                    title = title,
                    owner = me,
                    users = emptyList(),
                    createdAt = System.currentTimeMillis()
                )


                Log.d("ChatRoomListViewModel",room.toString())
                chatRoomRepository.createChatRoom(room.toDto())
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun switchTab(tab:ChatRoomTab) {
        _state.value = _state.value.copy(currentTab = tab)
    }

    fun removeChatRoom(roomId: Int) {
        viewModelScope.launch {
            try {
                val room = chatRoomRepository.getRoomFromRemote(roomId)
                if(room != null){
                    chatRoomRepository.deleteChatRoomToRemote(roomId)
                }

            } catch (e: Exception) {

            }
        }
    }


    //---------------------MQTT--------------------------

    private val topics = listOf("message")
    private fun subscribeToMqttTopics(){
        MqttClient.connect()
        MqttClient.setOnMessageReceived { topic, message -> handleReceivedMessage(topic,message)}
        topics.forEach { MqttClient.subscribe("liontalk/rooms/+/$it") }
    }
    private fun handleReceivedMessage(topic:String, message:String) {
        when {
            topic.endsWith("/message") -> onReceivedMessage(message)
        }
    }
    private fun onReceivedMessage(message:String) {
        try {
            val dto = Gson().fromJson(message,ChatMessageDto::class.java)
            viewModelScope.launch {
                val room = withContext(Dispatchers.IO) {
                    chatRoomRepository.getChatRoom(dto.roomId)
                }

                val unReadCount = withContext(Dispatchers.IO) {
                    chatMessageRepository.fetchUnreadCountFromServer(dto.roomId,
                        room?.lastReadMessageId
                    )
                }
                withContext(Dispatchers.IO) {
                    chatRoomRepository.updateUnReadCount(dto.roomId, unReadCount)
                }
            }
        } catch (e: Exception) {
        }
    }
    //---------------------MQTT--------------------------
}