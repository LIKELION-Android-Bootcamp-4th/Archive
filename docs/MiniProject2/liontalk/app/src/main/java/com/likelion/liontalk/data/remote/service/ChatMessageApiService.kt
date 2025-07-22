package com.likelion.liontalk.data.remote.service

import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatMessageApiService {
    @POST("messages")
    suspend fun sendMessage(@Body message:ChatMessageDto) : Response<ChatMessageDto>
}