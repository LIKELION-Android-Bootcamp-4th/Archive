package com.likelion.liontalk.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.datasource.ChatRoomLocalDataSource
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.data.remote.datasource.ChatRoomRemoteDataSource
import com.likelion.liontalk.data.remote.dto.ChatRoomDto
import com.likelion.liontalk.data.remote.dto.addUserIfNotExists
import com.likelion.liontalk.model.ChatRoom
import com.likelion.liontalk.model.ChatRoomMapper.toEntity
import com.likelion.liontalk.model.ChatRoomMapper.toModel
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatRoomRepository(context: Context) {
    private val remote = ChatRoomRemoteDataSource()
    private val local = ChatRoomLocalDataSource(context)

    //loca room db에서 chatroomentity 목록을 가져온다.
    fun getChatRoomEntities() : LiveData<List<ChatRoomEntity>> {
        return local.getChatRooms()
    }

    fun getChatRoomsFlow(): Flow<List<ChatRoom>> {
        return local.getChatRoomsFlow().map { it.mapNotNull { entity -> entity.toModel() } }
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

            Log.d("Sync","remote : $remoteRooms")

            local.clear()

            Log.d("Sync","로컬 DB에 채팅방 데이터 저장...중")
            local.insertAll(entities)            // local room insert many
            Log.d("Sync","로컬 DB 저장 완료")

            val localRooms = local.getChatRoomsList()
            Log.d("Sync","local : $localRooms")
            val dbCount = local.getCount()
            Log.d("Sync","로컬 DB 저장 완료 : $dbCount")

        } catch (e: Exception ) {
            Log.e("Sync", "채팅방 동기화 중 오류 발생: ${e.message}", e)
            throw e
        }
    }

    // 서버 및 로컬 room db 입장 처리
    suspend fun enterRoom(user:ChatUser,roomId: Int): ChatRoom {
        //1.서버로 부터 최신 룸 정보를 가져옴
        val remoteRoom = remote.fetchRoom(roomId)
        Log.d("SCOTT","originalRoom:$remoteRoom")
        val requestDto = remoteRoom.addUserIfNotExists(user)

        val updatedRoom = remote.updateRoom(requestDto)
        Log.d("SCOTT","updatedRoom:$updatedRoom")
        if(updatedRoom != null) {
            local.updateUsers(roomId,updatedRoom.users)
        }
        return updatedRoom?.toModel() ?: throw Exception("서버 입장 처리 실패")
    }



}