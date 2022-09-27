package com.example.withpet.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomeBoard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name="region") val region: String,
    @ColumnInfo(name="town") val town: String,
    @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="content") val content: String,
    @ColumnInfo(name="time") val time: String,
    @ColumnInfo(name="price") val price: String,
    @ColumnInfo(name="board_uid") val uid: String,
    @ColumnInfo(name="image") val image: Bitmap

)