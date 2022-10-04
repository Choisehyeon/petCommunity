package com.example.withpet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.withpet.converters.UserListConverters
import com.example.withpet.dao.WithBoardDao
import com.example.withpet.entity.WithBoard

@Database(entities = [WithBoard::class], version = 4)
@TypeConverters(UserListConverters::class)
abstract class WithBoardDatabase : RoomDatabase() {

    abstract fun WithBoardDao() : WithBoardDao

    companion object {
        @Volatile
        private var INSTANCE : WithBoardDatabase ?= null

        fun getDatabase(
            context: Context
        ) : WithBoardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WithBoardDatabase::class.java,
                    "withBoard_database"

                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}