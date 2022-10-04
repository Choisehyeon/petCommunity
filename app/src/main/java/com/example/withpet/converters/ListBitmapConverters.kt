package com.example.withpet.converters

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.google.gson.Gson

class ListBitmapConverters {

    @TypeConverter
    fun listToJosn(value : List<Bitmap>? ): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value : String) : List<Bitmap>? {
        return Gson().fromJson(value, Array<Bitmap>::class.java)?.toList()
    }
}