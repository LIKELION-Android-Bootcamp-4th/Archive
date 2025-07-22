package com.likelion.liontalk.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.likelion.liontalk.model.ChatUser

class Converter {

    private val gson = Gson()
    // List<String> -> jsonString to Room
    @TypeConverter
    fun fromStringList(value: List<String>?):String {
        return gson.toJson(value)
    }

    // RoomDB jsonString -> List<String>
    @TypeConverter
    fun toStringList(value: String) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value,listType)
    }

    @TypeConverter
    fun fromUser(value: ChatUser): String = gson.toJson(value)

    @TypeConverter
    fun toUser(value: String) : ChatUser = gson.fromJson(value, ChatUser::class.java)

    @TypeConverter
    fun toUserList(value: String): List<ChatUser> {
        val listType = object : TypeToken<List<ChatUser>>() {}.type
        return gson.fromJson(value,listType)
    }

    @TypeConverter
    fun fromUserList(value: List<ChatUser>) : String {
        return gson.toJson(value)
    }

}