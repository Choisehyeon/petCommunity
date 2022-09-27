package com.example.withpet.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.withpet.converters.Converters

@Entity
data class Bookmark(
    @PrimaryKey
    @ColumnInfo(name="id")
    val id : String,
    @ColumnInfo(name="userId") val user_id : String,
    @ColumnInfo(name= "boardId") val board_id : Long
)
