package com.example.withpet.converters

import android.graphics.Bitmap
import android.icu.text.IDNA
import androidx.room.TypeConverter
import com.example.withpet.entity.InfoImage
import com.google.gson.Gson

class ListStringConverters {

    @TypeConverter
    fun listToJosn(value : List<String>? ): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value :String) : List<String>? {
        return Gson().fromJson(value, Array<String>::class.java)?.toList()
    }
}