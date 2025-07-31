package com.likelion.liontalk.data.local.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

class ChatMessageLocalDataSource(context: Context) {
    private val dao = AppDatabase.getInstance(context).chatMessageDao()

    suspend fun clear() {
        dao.clear()
    }

    suspend fun insert(message: ChatMessageEntity) {
        dao.insert(message)
    }

    fun getMessageForRoom(roomId: String) : LiveData<List<ChatMessageEntity>> {
        return dao.getMessagesForRoom(roomId)
    }

    fun getMessageForRoomFlow(roomId: String) : Flow<List<ChatMessageEntity>> {
        return dao.getMessagesForRoomFlow(roomId)
    }

    suspend fun getMessages(roomId: String) : List<ChatMessageEntity> {
        return dao.getMessages(roomId)
    }

    suspend fun getLatestMessage(roomId: String):ChatMessageEntity ? {
        return dao.getLatestMessage(roomId)
    }

    suspend fun deleteMessagesByRoomId(roomId:String) {
        dao.deleteMessagesByRoomId(roomId)
    }

    suspend fun insertAll(messages: List<ChatMessageEntity>) {
        dao.insertAll(messages)
    }

    suspend fun getUnreadMessageCount(roomId: String, lastReadMessageId: String): Int {
        return dao.getUnreadMessageCount(roomId,lastReadMessageId)
    }
}