package com.likelion.liontalk.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.datasource.ChatRoomLocalDataSource
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.data.remote.datasource.ChatRoomRemoteDataSource
import com.likelion.liontalk.data.remote.dto.ChatRoomDto
import com.likelion.liontalk.model.ChatRoomMapper.toEntity

class ChatRoomRepository(context: Context) {
    private val remote = ChatRoomRemoteDataSource()
    private val local = ChatRoomLocalDataSource(context)

    //loca room db에서 chatroomentity 목록을 가져온다.
    fun getChatRoomEntities() : LiveData<List<ChatRoomEntity>> {
        return local.getChatRooms()
    }

    suspend fun createChatRoom(chatRoom:ChatRoomDto){
        val chatroomDto = remote.createRoom(chatRoom)
        if (chatroomDto != null) {
            local.insert(chatroomDto.toEntity())
        }
    }

    suspend fun deleteChatRoomToRemote(roomId: Int) {
        remote.deleteRoom(roomId)
    }
    // sync : remote to local
    suspend fun syncFromServer() {
        try {
            Log.d("Sync","서버에서 채팅방 목록을 가져오는중...")
            val remoteRooms = remote.fetchRooms()       //원격 채팅방 목록
            Log.d("Sync","${remoteRooms.size} 개의 채팅바을 가져옴.")

            val entities = remoteRooms.map { it.toEntity()} //local entity 변환
            Log.d("Sync","${entities.size}개의 Entity 변환")

            local.clear()

            Log.d("Sync","로컬 DB에 채팅방 데이터 저장...중")
            local.insertAll(entities)            // local room insert many
            Log.d("Sync","로컬 DB 저장 완료")

            val dbCount = local.getCount()
            Log.d("Sync","로컬 DB 저장 완료 : $dbCount")

        } catch (e: Exception ) {
            Log.e("Sync", "채팅방 동기화 중 오류 발생: ${e.message}", e)
            throw e
        }
    }



}