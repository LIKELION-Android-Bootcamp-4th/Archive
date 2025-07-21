package com.likelion.liontalk.data.local.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.likelion.liontalk.data.local.AppDatabase
import com.likelion.liontalk.data.local.entity.ChatRoomEntity

class ChatRoomLocalDataSource(context: Context) {
    private val dao = AppDatabase.create(context).chatRoomDao()

    fun getChatRooms() : LiveData<List<ChatRoomEntity>> {
        return dao.getChatRooms()
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

    suspend fun clear() {
        dao.clear()
    }

    suspend fun getCount() : Int {
        return dao.getCount()
    }
}