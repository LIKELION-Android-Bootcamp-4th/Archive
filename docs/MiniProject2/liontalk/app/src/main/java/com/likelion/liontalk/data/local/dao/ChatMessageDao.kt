package com.likelion.liontalk.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.likelion.liontalk.data.local.entity.ChatMessageEntity

@Dao
interface ChatMessageDao {
    @Insert
    suspend fun insert(message: ChatMessageEntity)

    @Query("SELECT * FROM chat_message WHERE roomId = :roomId ORDER BY id DESC")
    fun getMessagesForRoom(roomId: Int) : LiveData<List<ChatMessageEntity>>
}