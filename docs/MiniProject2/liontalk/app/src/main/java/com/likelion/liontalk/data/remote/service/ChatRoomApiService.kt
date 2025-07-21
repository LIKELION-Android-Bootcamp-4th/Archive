package com.likelion.liontalk.data.remote.service

import com.likelion.liontalk.data.remote.dto.ChatRoomDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatRoomApiService {
    @GET("chatrooms")
    suspend fun getChatRooms(): List<ChatRoomDto>

    @POST("chatrooms")
    suspend fun createRoom(@Body chatRoom: ChatRoomDto) : Response<ChatRoomDto>

    @DELETE("chatrooms/{id}")
    suspend fun deleteRoom(@Path("id") id: Int) : Response<Unit>
}