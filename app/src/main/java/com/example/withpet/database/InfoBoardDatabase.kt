package com.example.withpet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.withpet.converters.Converters
import com.example.withpet.converters.ListStringConverters
import com.example.withpet.dao.InfoBoardDao
import com.example.withpet.entity.InfoBoard


@Database(entities = [InfoBoard::class], version = 10)
@TypeConverters(ListStringConverters::class, Converters::class)
abstract class InfoBoardDatabase : RoomDatabase() {

    abstract fun infoBoardDao() : InfoBoardDao

    companion object {
        @Volatile
        private var INSTANCE : InfoBoardDatabase ?= null

        fun getDatabase(
            context: Context
        ) : InfoBoardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InfoBoardDatabase::class.java,
                    "infoBoard_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}