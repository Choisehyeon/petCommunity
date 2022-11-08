package com.example.withpet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.withpet.converters.Converters
import com.example.withpet.converters.UserConverters
import com.example.withpet.dao.CommentDao
import com.example.withpet.entity.Comment

@Database(entities = [Comment::class], version = 6)
@TypeConverters(Converters::class)
abstract class CommentDatabase : RoomDatabase() {

    abstract fun commentDao() : CommentDao

    companion object {
        @Volatile
        private var INSTANCE : CommentDatabase ?= null

        fun getDatabase(
            context:Context
        ) : CommentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommentDatabase::class.java,
                    "comment_database"
                )

                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}