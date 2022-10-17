package com.example.withpet.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id : Long,
    @ColumnInfo(name="board_id")
    val board_id: Long,
    @ColumnInfo(name="write_user")
    val user : User,
    @ColumnInfo(name="write_time")
    val time : String,
    @ColumnInfo(name = "comment")
    val comment : String
)