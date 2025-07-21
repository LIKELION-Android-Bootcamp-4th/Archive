package com.likelion.liontalk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.likelion.liontalk.data.local.converter.Converter
import com.likelion.liontalk.data.local.dao.ChatRoomDao
import com.likelion.liontalk.data.local.entity.ChatRoomEntity

@TypeConverters(Converter::class)
@Database(entities = [ChatRoomEntity::class], version = 2)
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