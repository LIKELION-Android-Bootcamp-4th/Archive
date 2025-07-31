package com.likelion.liontalk.data.remote.datasource

import android.util.Log
import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import com.likelion.liontalk.model.ChatMessageMapper.toRequest
import com.likelion.liontalk.network.SupabaseClientProvider
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.FilterOperator

class ChatMessageSupabaseDataSource {
    private val client = SupabaseClientProvider.client

    // 메시지 전송
    suspend fun sendMessage(dto: ChatMessageDto): ChatMessageDto {
        val requestDto = dto.toRequest()
        return client.postgrest["chat_message"]
            .insert(requestDto)
            .decodeSingle<ChatMessageDto>()
    }

    // 모든 메시지 가져오기
    suspend fun fetchMessages(): List<ChatMessageDto> {
        return client.postgrest["chat_message"]
            .select()
            .decodeList<ChatMessageDto>()
    }

    // 특정 roomId의 메시지 가져오기
    suspend fun fetchMessagesByRoomId(roomId: Int): List<ChatMessageDto> {
        return client.postgrest["chat_message"]
            .select {
                filter("room_id", FilterOperator.EQ, roomId)
            }
            .decodeList<ChatMessageDto>()
    }
}