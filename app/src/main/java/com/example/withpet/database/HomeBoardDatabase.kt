package com.example.withpet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.withpet.converters.Converters
import com.example.withpet.dao.HomeBoardDao
import com.example.withpet.entity.HomeBoard

@Database(entities = [HomeBoard::class], version = 3)
@TypeConverters(Converters::class)
abstract class HomeBoardDatabase : RoomDatabase() {

    abstract fun HomeBoardDao() : HomeBoardDao

    companion object {
        @Volatile
        private var INSTANCE : HomeBoardDatabase ?= null

        fun getDatabase(
            context: Context
        ) : HomeBoardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HomeBoardDatabase::class.java,
                    "homeBoard_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}