package com.example.withpet.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.withpet.entity.User
import com.google.gson.Gson


class UserConverters {
    @TypeConverter
    fun StringToUser(string : String?) : User? {
        return Gson().fromJson(string, User::class.java)
    }
    @TypeConverter
    fun UserToString(user : User?) : String? {
        return Gson().toJson(user)
    }
}