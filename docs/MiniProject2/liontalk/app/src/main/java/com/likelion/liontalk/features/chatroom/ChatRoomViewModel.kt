package com.likelion.liontalk.features.chatroom

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import com.likelion.liontalk.data.remote.dto.TypingMessageDto
import com.likelion.liontalk.data.remote.mqtt.MqttClient
import com.likelion.liontalk.data.repository.ChatMessageRepository
import com.likelion.liontalk.data.repository.UserPreferenceRepository
import com.likelion.liontalk.model.ChatMessageMapper.toEntity
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatRoomViewModel(application: Application, private val roomId: Int) : ViewModel(){
    private val chatMessageRepository = ChatMessageRepository(application)
    val messages : LiveData<List<ChatMessageEntity>> = chatMessageRepository.getMessagesForRoom(roomId)

    private val userPreferenceRepository = UserPreferenceRepository.getInstance(application)
    val me : ChatUser get() = userPreferenceRepository.requireMe()


    private val _event = MutableSharedFlow<ChatRoomEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {

            userPreferenceRepository.loadUserFromStorage()

            withContext(Dispatchers.IO) {
                MqttClient.connect()
            }

            withContext(Dispatchers.IO) {
                subscribeToMqttTopics()
            }
        }
    }

    // 메세지 전송
    fun sendMessage(sender: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dto = ChatMessageDto(
                roomId = roomId,
                sender = me,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            // api send and local insert
            val responseDto = chatMessageRepository.sendMessage(dto)

            // mqtt send
            if (responseDto != null) {
                val json = Gson().toJson(responseDto)
                MqttClient.publish("liontalk/rooms/$roomId/message", json)
            }

            //chatMessageDao.insert(messageEntity)
        }
    }

    // MQTT - methods
    private val topics = listOf("message","typing")
    //MQTT 구독 및 메세지 수신 처리
    private fun subscribeToMqttTopics() {
//        MqttClient.connect()
        MqttClient.setOnMessageReceived { topic, message -> handleIncomingMqttMessage(topic,message) }
        topics.forEach {
            MqttClient.subscribe("liontalk/rooms/$roomId/$it")
        }
    }
    //MQTT 구독 해지
    private fun unsubscribeFromMqttTopics() {
        topics.forEach {
            MqttClient.unsubscribe("liontalk/rooms/$roomId/$it")
        }
    }

    // MQTT 수신 메세지 처리
    private fun handleIncomingMqttMessage(topic: String,message: String) {
        when {
            topic.endsWith("/message") -> onReceivedMessage(message)
            topic.endsWith("/typing") -> onReceivedTyping(message)
        }
    }

    private fun onReceivedMessage(message: String) {
        val dto = Gson().fromJson(message,ChatMessageDto::class.java)

        viewModelScope.launch {
//            chatMessageDao.insert(dto.toEntity())
            chatMessageRepository.receiveMessage(dto)
        }
    }
//    private val _event = MutableSharedFlow<ChatRoomEvent>()
//    val event = _event.asSharedFlow()

    private fun onReceivedTyping(message: String) {
        val dto = Gson(). fromJson(message, TypingMessageDto::class.java)
        if (dto.sender != me.name) {
            viewModelScope.launch {
                val event = if (dto.typing) ChatRoomEvent.TypingStarted(dto.sender)
                else ChatRoomEvent.TypingStopped

                _event.emit(event)
            }
        }
    }

    private var typing = false
    private var typingStopJop: Job? = null

    fun onTypingChanged(text: String) {
        if (text.isNotBlank() && !typing) {
            publishtypingStatus(true)
        }
        typingStopJop?.cancel()
        typingStopJop = viewModelScope.launch {
            delay(2000)
            typing = false
            publishtypingStatus(false)
        }
    }

    private fun publishtypingStatus(isTyping:Boolean) {
        val json = Gson().toJson(TypingMessageDto(sender = me.name,isTyping))
        MqttClient.publish("liontalk/rooms/$roomId/typing", json)
    }
}