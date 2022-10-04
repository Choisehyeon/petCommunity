package com.example.withpet.entity

import androidx.room.*
import com.example.withpet.converters.Converters
import java.io.Serializable

@Entity
data class Bookmark(
    @PrimaryKey
    @ColumnInfo(name="id")
    val id : String,
    @ColumnInfo(name="userId") val user_id : String,
    @ColumnInfo(name= "boardId") val board_id: Long
)
