package com.likelion.liontalk.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.likelion.liontalk.data.local.dao.ChatRoomDao

abstract class AppDatabase : RoomDatabase(){
    abstract fun chatRoomDao() : ChatRoomDao

    companion object {
        fun create(content : Context) : AppDatabase = Room.databaseBuilder(
            content.applicationContext,
            AppDatabase:: class.java,
            "chat_db"
        ).fallbackToDestructiveMigration().build()
    }
}