package com.likelion.liontalk.features.chatroom

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import com.likelion.liontalk.data.remote.mqtt.MqttClient
import com.likelion.liontalk.data.repository.ChatMessageRepository
import com.likelion.liontalk.model.ChatMessageMapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatRoomViewModel(application: Application, private val roomId: Int) : ViewModel(){
//    private val chatMessageDao = AppDatabase.create(application)
//        .chatMessageDao()

    private val chatMessageRepository = ChatMessageRepository(application)

//    val messages : LiveData<List<ChatMessageEntity>>
//        = chatMessageDao.getMessagesForRoom(roomId)

    val messages : LiveData<List<ChatMessageEntity>> = chatMessageRepository.getMessagesForRoom(roomId)

    init {
        viewModelScope.launch {

            chatMessageRepository.clearLocalDB()

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
                sender = sender,
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
    private val topics = listOf("message")
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
        }
    }

    private fun onReceivedMessage(message: String) {
        val dto = Gson().fromJson(message,ChatMessageDto::class.java)

        viewModelScope.launch {
//            chatMessageDao.insert(dto.toEntity())
            chatMessageRepository.receiveMessage(dto)
        }
    }
}