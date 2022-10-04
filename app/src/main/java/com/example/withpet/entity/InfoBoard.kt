package com.example.withpet.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InfoBoard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "region") val reiong: String,
    @ColumnInfo(name = "town") val town: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "imgList") val imgList: List<Bitmap>,
    @ColumnInfo(name = "place") val place : String,
    @ColumnInfo(name = "board_uid") val board_uid : String,
    @ColumnInfo(name = "write_time") val write_time : String,
    @ColumnInfo(name="board_nickname") val nickname: String,
)