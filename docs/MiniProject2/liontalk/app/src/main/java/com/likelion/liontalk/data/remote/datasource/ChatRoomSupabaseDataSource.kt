package com.likelion.liontalk.data.remote.datasource

import android.util.Log
import com.likelion.liontalk.data.remote.dto.ChatRoomDto
import com.likelion.liontalk.model.ChatRoomMapper.toRequest
import com.likelion.liontalk.model.ChatUser
import com.likelion.liontalk.network.SupabaseClientProvider
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.FilterOperator

class ChatRoomSupabaseDataSource {
    private val client = SupabaseClientProvider.client

    // 전체 채팅방 목록
    suspend fun fetchRooms(): List<ChatRoomDto> {
        return client.postgrest["chat_room"]
            .select()
            .decodeList<ChatRoomDto>()
    }

    // 특정 채팅방 상세
    suspend fun fetchRoom(id: Int): ChatRoomDto {
        return client.postgrest["chat_room"]
            .select {
                filter("id", FilterOperator.EQ,id)
            }
            .decodeSingle<ChatRoomDto>()
    }


    // 채팅방 수정
    suspend fun updateRoom(dto: ChatRoomDto): ChatRoomDto {

        Log.d("ChatRoomSupabaseDataSource", "dto:$dto")

        val result = client.postgrest["chat_room"]
            .update(dto) {
                filter("id", FilterOperator.EQ, dto.id)
            }
            .decodeList<ChatRoomDto>()

        if (result.isEmpty()) {
            throw Exception("업데이트할 채팅방(id=${dto.id})이 없습니다.")
        }

        return result.first()
    }

    // 채팅방 생성
    suspend fun createRoom(dto: ChatRoomDto): ChatRoomDto {

        val requestDto = dto.toRequest()

        try {

            val result = client.postgrest["chat_room"]
                .insert(requestDto)
                .decodeSingle<ChatRoomDto>()

            Log.d("ChatRoomSupabaseDataSource", "result:$result")

            return result
        } catch (e:Exception) {
            Log.e("ChatRoomSupabaseDataSource", "error:${e.message}")
            throw e
        }

//        return client.postgrest["chat_room"]
//            .insert(chatRoomDto)
//            .decodeSingle<ChatRoomDto>()
    }

    // 채팅방 삭제
    suspend fun deleteRoom(id: Int) {
        client.postgrest["chat_room"].delete {
            filter("id", FilterOperator.EQ,id)
        }
    }
}