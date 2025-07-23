package com.likelion.liontalk.data.remote.service

import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatMessageApiService {
    @POST("messages")
    suspend fun sendMessage(@Body message:ChatMessageDto) : Response<ChatMessageDto>

    @GET("messages")
    suspend fun fetchMessagesByRoomId(@Query("roomId") roomId:Int):List<ChatMessageDto>
}