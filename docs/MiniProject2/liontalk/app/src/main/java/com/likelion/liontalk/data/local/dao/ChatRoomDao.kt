package com.likelion.liontalk.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.model.ChatUser

@Dao
interface ChatRoomDao {
    // I/O 작업시에는 비동기 처리 해야함.
    // 채팅룸 생성 : ID 중복인 경우 대체
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chatroom : ChatRoomEntity)

    @Delete
    suspend fun delete(chatroom : ChatRoomEntity)

    // 전체 채팅룸 목록 가져오기
    @Query("SELECT * FROM chat_room ORDER BY id desc")
    fun getChatRooms() : LiveData<List<ChatRoomEntity>>

    // id 에 해당하는 채팅룸 데이터 가져오기
    @Query("SELECT * FROM chat_room WHERE id=:id")
    fun getChatRoom(id : Int) : ChatRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(chatRooms : List<ChatRoomEntity>)

    // 채팅방의 사용자 정보만 업데이트 함.
    @Query("UPDATE chat_room SET users = :users WHERE id = :id")
    suspend fun updateUsers(id:Int, users:List<ChatUser>)

    @Query("SELECT COUNT(*) FROM chat_room")
    suspend fun getCount():Int

    @Query("DELETE FROM chat_room")
    suspend fun clear()
}