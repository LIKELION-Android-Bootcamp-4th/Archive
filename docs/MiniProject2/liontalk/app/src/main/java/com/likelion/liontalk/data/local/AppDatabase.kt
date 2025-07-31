package com.likelion.liontalk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.likelion.liontalk.data.local.converter.Converter
import com.likelion.liontalk.data.local.dao.ChatMessageDao
import com.likelion.liontalk.data.local.dao.ChatRoomDao
import com.likelion.liontalk.data.local.entity.ChatMessageEntity
import com.likelion.liontalk.data.local.entity.ChatRoomEntity

@TypeConverters(Converter::class)
@Database(entities = [ChatRoomEntity::class , ChatMessageEntity::class], version = 15)
abstract class AppDatabase : RoomDatabase(){
    abstract fun chatRoomDao() : ChatRoomDao
    abstract fun chatMessageDao() : ChatMessageDao

    companion object {
        private var _instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return _instance ?: synchronized(this) {
                _instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase:: class.java,
                    "chat_db",

                ).fallbackToDestructiveMigration()
                    .build()
                    .also { _instance = it }
            }
        }
    }

//    companion object {
//        fun create(content : Context) : AppDatabase = Room.databaseBuilder(
//            content.applicationContext,
//            AppDatabase:: class.java,
//            "chat_db"
//        ).fallbackToDestructiveMigration().build()
//    }
}