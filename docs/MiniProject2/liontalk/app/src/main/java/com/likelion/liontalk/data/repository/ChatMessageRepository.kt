package com.likelion.liontalk.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.datasource.ChatMessageLocalDataSource
import com.likelion.liontalk.data.local.datasource.ChatRoomLocalDataSource
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.data.remote.datasource.ChatMessageRemoteDataSource
import com.likelion.liontalk.data.remote.datasource.ChatMessageSupabaseDataSource
import com.likelion.liontalk.data.remote.dto.ChatMessageDto
import com.likelion.liontalk.model.ChatMessage
import com.likelion.liontalk.model.ChatMessageMapper.toEntity
import com.likelion.liontalk.model.ChatMessageMapper.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ChatMessageRepository(context: Context) {
//    private val remote = ChatMessageRemoteDataSource()
    private val local = ChatMessageLocalDataSource(context)
    private val remote = ChatMessageSupabaseDataSource()

    private val roomLocal = ChatRoomLocalDataSource(context)

    private val TAG = "ChatMessageRepository"
    suspend fun clearLocalDB() {
        local.clear()
    }

    // 현재 로컬 db에 저장된 메세지 목록을 가져옴.
    fun getMessagesForRoom(roomId: Int): LiveData<List<ChatMessageEntity>> {
        return local.getMessageForRoom(roomId)
    }

    suspend fun syncFromServer(roomId: Int){
        try {

            val room = roomLocal.getChatRoom(roomId)
            val lastReadMessageId = room?.lastReadMessageId ?:0

            Log.d(TAG, "서버에서 전체 메시지 목록을 가져오는 중...")
            val remoteMessages = remote.fetchMessagesByRoomId(roomId)
            Log.d(TAG, "서버에서 ${remoteMessages.size}개의 메시지를 가져옴")

            // lastReadMessageId 이후 메시지만 필터링
            val filteredMessages = remoteMessages
                .filter { it.roomId == roomId && it.id > lastReadMessageId }
            Log.d(TAG, "roomId=$roomId, lastReadMessageId=$lastReadMessageId 이후 메시지 ${filteredMessages.size}개 필터링")

            val entities = filteredMessages.map { it.toEntity() }
            if (entities.isNotEmpty()) {
                // 로컬 DB에 새 메시지 삽입
                local.insertAll(entities)
                Log.d(TAG, "roomId=$roomId 메시지 ${entities.size}개 로컬 저장 완료")
            } else {
                Log.d(TAG, "roomId=$roomId 신규 메시지 없음, 저장 스킵")
            }
        } catch (e: Exception) {
            Log.e(TAG, "roomId=$roomId 메시지 동기화 중 오류 발생: ${e.message}", e)
        }
    }

    suspend fun syncAllMessagesFromServer() {
        val remoteMessages = remote.fetchMessages()
        Log.d(TAG, "서버로 부터 ${remoteMessages.size}개 메시지를 가져옴")
        val chatRooms = roomLocal.getChatRoomsList()
        Log.d(TAG, "로컬로 부터 ${chatRooms.size}개 채팅방을 가져옴")

        // roomId → lastReadMessageId 맵 생성
        val lastReadMap = chatRooms.associateBy({ it.id }, { it.lastReadMessageId })
        val filtered = remoteMessages.filter { message ->
            val lastReadId = lastReadMap[message.roomId] ?: 0
            message.id > lastReadId
        }
        Log.d(TAG, "신규 메세지 ${filtered.size}개 필터링")

        if (filtered.isNotEmpty()) {
            local.insertAll(filtered.map { it.toEntity() })
            Log.d("Sync", "신규 메시지 ${filtered.size}개 로컬 저장 완료")
        } else {
            Log.d("Sync", "신규 메시지 없음, 저장 스킵")
        }

    }

    fun getMessagesForRoomFlow(roomId: Int): Flow<List<ChatMessage>> {
        return local.getMessageForRoomFlow(roomId)
            .map { entity -> entity
                .map { it.toModel() } }
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

    suspend fun fetchUnreadCountFromServer(roomId:Int, lastReadMessageId: Int?):Int {
        val remoteMessages = remote.fetchMessagesByRoomId(roomId)
        Log.d("UnreadCount", "roomId=$roomId, lastReadMessageId=$lastReadMessageId")
        Log.d("UnreadCount", "fetched ${remoteMessages.size} messages")
        if (lastReadMessageId == null) return remoteMessages.size

        val index = remoteMessages.indexOfFirst { it.id == lastReadMessageId }
        Log.d("UnreadCount", "index : $index")
        return if (index == -1) {
            remoteMessages.size
        } else {
            remoteMessages.drop(index + 1).size
        }
    }

    suspend fun getLatestMessage(roomId: Int) : ChatMessage? {
        return local.getLatestMessage(roomId)?.toModel()
    }
}