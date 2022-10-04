package com.example.withpet.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WithBoard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id : Long,
    @ColumnInfo(name="region") val reiong : String,
    @ColumnInfo(name="town") val town : String,
    @ColumnInfo(name="title") val title : String,
    @ColumnInfo(name="content") val content : String,
    @ColumnInfo(name="people") val people : Int,
    @ColumnInfo(name="gender") val gender : String,
    @ColumnInfo(name="age") val age : String,
    @ColumnInfo(name="date") val date : String,
    @ColumnInfo(name="time") val time : String,
    @ColumnInfo(name="place") val place :String,
    @ColumnInfo(name="writeTime") val writeTime : String,
    @ColumnInfo(name="board_uid") val board_uid : String,
    @ColumnInfo(name="board_nickname") val nickname: String,
    @ColumnInfo(name="participantList") val participants : List<User>?
)
