package com.example.withpet.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id : Long,
    @ColumnInfo(name="board_id")
    val board_id: Long,
    @Embedded(prefix = "user")
    val user : User,
    @ColumnInfo(name="write_time")
    val time : String,
    @ColumnInfo(name = "comment")
    val comment : String
)