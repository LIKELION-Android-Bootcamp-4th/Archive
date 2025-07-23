package com.likelion.liontalk.data.local.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.flow.Flow

class ChatRoomLocalDataSource(context: Context) {
    private val dao = AppDatabase.create(context).chatRoomDao()

    fun getChatRooms() : LiveData<List<ChatRoomEntity>> {
        return dao.getChatRooms()
    }

    fun getChatRoomsList() : List<ChatRoomEntity> {
        return dao.getChatRoomsList()
    }

    fun getChatRoomsFlow() : Flow<List<ChatRoomEntity>> {
        return dao.getChatRoomsFlow()
    }

    fun getChatRoom(roomId: Int) : ChatRoomEntity {
        return dao.getChatRoom(roomId)
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

    suspend fun updateUsers(id:Int,users:List<ChatUser>) {
        dao.updateUsers(id,users)
    }

    suspend fun clear() {
        dao.clear()
    }

    suspend fun getCount() : Int {
        return dao.getCount()
    }

    suspend fun updateLastReadMessageId(id: Int, lastReadMessageId:Int) {
        dao.updateLastReadMessageId(id,lastReadMessageId)
    }

    suspend fun updateUnReadCount(id: Int, unReadCount: Int) {
        dao.updateUnReadCount(id, unReadCount)
    }

    suspend fun updateLockStatus(id: Int, isLocked: Boolean) {
        dao.updateLockStatus(id, isLocked)
    }
}