package com.likelion.liontalk.data.remote.datasource

import com.likelion.liontalk.data.remote.dto.ChatRoomDto
import com.likelion.liontalk.data.remote.service.ChatRoomApiService
import com.likelion.liontalk.network.RetrofitProvider

class ChatRoomRemoteDataSource {

    val api = RetrofitProvider.create(ChatRoomApiService::class.java)

    suspend fun fetchRooms(): List<ChatRoomDto> {
        return api.getChatRooms()
    }

    suspend fun fetchRoom(id: Int) : ChatRoomDto {
        return api.getChatRoom(id)
    }

    suspend fun updateRoom(dto: ChatRoomDto) :ChatRoomDto? {
        val response = api.updateChatRoom(dto.id, dto)
        if(!response.isSuccessful) {
            throw Exception("서버 업데이트 실패:${response.code()} ${response.message()} ")
        }
        return response.body()
    }

    suspend fun createRoom(chatRoomDto: ChatRoomDto): ChatRoomDto? {
        val response = api.createRoom(chatRoomDto)
        if(!response.isSuccessful) {
            throw Exception("서버 채팅방 생성 실패:${response.message()} , ${response.code()}")
        }
        return response.body()
    }

    suspend fun deleteRoom(id: Int) {
        val response = api.deleteRoom(id)
        if(!response.isSuccessful) {
            throw Exception("서버 채팅방 삭제 실패:${response.message()} , ${response.code()}")
        }
    }
}