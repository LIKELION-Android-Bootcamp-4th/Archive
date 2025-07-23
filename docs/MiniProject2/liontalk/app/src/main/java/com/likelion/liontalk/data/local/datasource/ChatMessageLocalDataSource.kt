package com.likelion.liontalk.data.local.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

class ChatMessageLocalDataSource(context: Context) {
    private val dao = AppDatabase.create(context).chatMessageDao()

    suspend fun clear() {
        dao.clear()
    }

    suspend fun insert(message: ChatMessageEntity) {
        dao.insert(message)
    }

    fun getMessageForRoom(roomId: Int) : LiveData<List<ChatMessageEntity>> {
        return dao.getMessagesForRoom(roomId)
    }

    fun getMessageForRoomFlow(roomId: Int) : Flow<List<ChatMessageEntity>> {
        return dao.getMessagesForRoomFlow(roomId)
    }

    suspend fun getMessages(roomId: Int) : List<ChatMessageEntity> {
        return dao.getMessages(roomId)
    }

    suspend fun getLatestMessage(roomId: Int):ChatMessageEntity ? {
        return dao.getLatestMessage(roomId)
    }
}