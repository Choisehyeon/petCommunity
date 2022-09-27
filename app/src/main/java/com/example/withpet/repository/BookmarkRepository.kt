package com.example.withpet.repository

import android.app.Application
import com.example.withpet.dao.BookmarkDao
import com.example.withpet.database.BookmarkDatabase
import com.example.withpet.entity.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookmarkRepository(application: Application) {

    private val bookmarkDao : BookmarkDao

    init {
        val db = BookmarkDatabase.getDatabase(application)
        bookmarkDao = db.bookmarkDao()
    }

    fun insert(bookmark : Bookmark) {
        CoroutineScope(Dispatchers.IO).launch {
            bookmarkDao.insert(bookmark)
        }
    }

    fun delete(bookmark: Bookmark) {
        CoroutineScope(Dispatchers.IO).launch{
            bookmarkDao.delete(bookmark)
        }
    }

    fun getBookmarkList(uid : String, completed : (List<Bookmark>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(bookmarkDao.getBookmarkList(uid))
        }
    }

    fun getBookmarkIdList(uid : String, completed: (List<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(bookmarkDao.getBookmarkIdList(uid))
        }
    }
}