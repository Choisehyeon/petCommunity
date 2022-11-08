package com.example.withpet.database

import android.content.Context
import androidx.room.*
import com.example.withpet.converters.Converters
import com.example.withpet.dao.UserDao
import com.example.withpet.entity.User

@Database(entities = [User::class], version = 3)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {

        @Volatile
        private var INSTANCE : UserDatabase ?= null

        fun getDatabase(
            context: Context
        ) : UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}