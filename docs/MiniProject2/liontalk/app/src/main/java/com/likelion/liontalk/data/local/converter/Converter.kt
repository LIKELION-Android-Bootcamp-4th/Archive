package com.likelion.liontalk.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    // List<String> -> jsonString to Room
    @TypeConverter
    fun fromStringList(value: List<String>?):String {
        return Gson().toJson(value)
    }

    // RoomDB jsonString -> List<String>
    @TypeConverter
    fun toStringList(value: String) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value,listType)
    }
}