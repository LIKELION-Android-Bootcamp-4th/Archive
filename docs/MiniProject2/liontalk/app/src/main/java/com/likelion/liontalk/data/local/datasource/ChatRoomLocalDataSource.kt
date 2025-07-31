package com.likelion.liontalk.data.local.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.flow.Flow

class ChatRoomLocalDataSource(context: Context) {
    private val dao = AppDatabase.getInstance(context).chatRoomDao()

    fun getChatRooms() : LiveData<List<ChatRoomEntity>> {
        return dao.getChatRooms()
    }

    fun getChatRoomsList() : List<ChatRoomEntity> {
        return dao.getChatRoomsList()
    }

    fun getChatRoomsFlow() : Flow<List<ChatRoomEntity>> {
        return dao.getChatRoomsFlow()
    }

    fun getChatRoom(roomId: String) : ChatRoomEntity? {
        return dao.getChatRoom(roomId)
    }

    fun getChatRoomFlow(roomId: String) : Flow<ChatRoomEntity?> {
        return dao.getChatRoomFlow(roomId)
    }

    suspend fun insert(chatRoom : ChatRoomEntity) {
        dao.insert(chatRoom)
    }

    suspend fun insertAll(chatRooms: List<ChatRoomEntity>) {
        dao.insertAll(chatRooms)
    }

    suspend fun delete(chatRoom : ChatRoomEntity) {
        dao.delete(chatRoom)
    }

    suspend fun updateUsers(id:String,users:List<ChatUser>) {
        dao.updateUsers(id,users)
    }

    suspend fun clear() {
        dao.clear()
    }

    suspend fun getCount() : Int {
        return dao.getCount()
    }

    suspend fun updateLastReadMessageId(id: String, lastReadMessageId:String) {
        dao.updateLastReadMessageId(id,lastReadMessageId)
    }

    suspend fun updateLastReadMessage(id: String, lastReadMessageId:String, lastReadMessageTimestamp:Long) {
        dao.updateLastReadMessage(id,lastReadMessageId,lastReadMessageTimestamp)
    }

    suspend fun updateUnReadCount(id: String, unReadCount: Int) {
        dao.updateUnReadCount(id, unReadCount)
    }

    suspend fun updateLockStatus(id: String, isLocked: Boolean) {
        dao.updateLockStatus(id, isLocked)
    }

    suspend fun deleteById(id:String) {
        dao.deleteById(id)
    }
}