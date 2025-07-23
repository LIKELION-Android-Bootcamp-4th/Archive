package com.likelion.liontalk.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: ChatMessageEntity)

    @Query("SELECT * FROM chat_message WHERE roomId = :roomId ORDER BY id ASC")
    fun getMessagesForRoom(roomId: Int) : LiveData<List<ChatMessageEntity>>

    @Query("SELECT * FROM chat_message WHERE roomId = :roomId ORDER BY id ASC")
    fun getMessagesForRoomFlow(roomId: Int) : Flow<List<ChatMessageEntity>>

    @Query("DELETE FROM chat_message")
    suspend fun clear()

    @Query("SELECT * FROM chat_message WHERE roomId =:roomId")
    suspend fun getMessages(roomId: Int) : List<ChatMessageEntity>

    @Query("SELECT * FROM chat_message WHERE roomId =:roomId ORDER BY id DESC LIMIT 1")
    suspend fun getLatestMessage(roomId: Int):ChatMessageEntity?

    @Query("DELETE FROM chat_message WHERE roomId = :roomId ")
    suspend fun deleteMessagesByRoomId(roomId:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<ChatMessageEntity>)
}