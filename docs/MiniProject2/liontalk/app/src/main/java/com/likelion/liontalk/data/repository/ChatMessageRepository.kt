package com.likelion.liontalk.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.datasource.ChatMessageLocalDataSource
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.data.remote.datasource.ChatMessageRemoteDataSource
import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import com.likelion.liontalk.model.ChatMessageMapper.toEntity

class ChatMessageRepository(context: Context) {
    private val remote = ChatMessageRemoteDataSource()
    private val local = ChatMessageLocalDataSource(context)

    suspend fun clearLocalDB() {
        local.clear()
    }

    // 현재 로컬 db에 저장된 메세지 목록을 가져옴.
    fun getMessagesForRoom(roomId: Int): LiveData<List<ChatMessageEntity>> {
        return local.getMessageForRoom(roomId)
    }

    // API 서버로 메세지를 보내고 로컬 db에 저장
    suspend fun sendMessage(message:ChatMessageDto):ChatMessageDto? {
        try {

            val chatMessages = local.getMessages(message.roomId)

            chatMessages.forEach { msg ->
                Log.d("CHAT_MSG","$msg")
            }


            val result = remote.sendMessage(message)
            result?.let {
                Log.d("ChatMessageRepository","created dto:$it")
                local.insert(it.toEntity())
                return it
            }
        } catch (e: Exception) {
            Log.e("ChatMessageRepository","${e.message}")
        }
        return null
    }

    // MQTT 수신 메세지 로컬 DB 저장
    suspend fun receiveMessage(message: ChatMessageDto) {
        local.insert(message.toEntity())
    }
}