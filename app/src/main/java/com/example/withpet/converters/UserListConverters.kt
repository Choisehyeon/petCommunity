package com.example.withpet.converters

import androidx.room.TypeConverter
import com.example.withpet.entity.User
import com.google.gson.Gson

class UserListConverters {

    @TypeConverter
    fun listToJson(value : List<User>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value : String): List<User>? {
        return Gson().fromJson(value, Array<User>::class.java)?.toList()
    }
}