package com.example.withpet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.withpet.dao.BookmarkDao
import com.example.withpet.entity.Bookmark

@Database(entities = [Bookmark::class], version = 4)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao() : BookmarkDao

    companion object {

        @Volatile
        private var INSTANCE : BookmarkDatabase ?= null

        fun getDatabase(
            context: Context
        ) : BookmarkDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}