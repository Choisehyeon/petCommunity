package com.example.withpet.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo(name="nickname") val nickname: String,
    @ColumnInfo(name="region") val region: String,
    @ColumnInfo(name="town") val town: String,
    @ColumnInfo(name="profile") val profile: Bitmap?
)
