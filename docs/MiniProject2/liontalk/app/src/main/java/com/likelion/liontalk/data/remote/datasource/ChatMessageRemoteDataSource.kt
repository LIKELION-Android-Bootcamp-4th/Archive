package com.likelion.liontalk.data.remote.datasource

import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import com.likelion.liontalk.data.remote.service.ChatMessageApiService
import com.likelion.liontalk.network.RetrofitProvider

class ChatMessageRemoteDataSource {
    private val api : ChatMessageApiService = RetrofitProvider.create(ChatMessageApiService::class.java)

    suspend fun sendMessage(message: ChatMessageDto):ChatMessageDto ? {
        val response = api.sendMessage(message)
        if(!response.isSuccessful) {
            throw Exception("서버 메세지 전송 실패 : [${response.code()}] ${response.message()}")
        }
        return response.body()
    }

    suspend fun fetchMessagesByRoomId(roomId: Int):List<ChatMessageDto> {
        return api.fetchMessagesByRoomId(roomId)
    }
}