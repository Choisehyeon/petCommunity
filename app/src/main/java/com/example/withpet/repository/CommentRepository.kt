package com.example.withpet.repository

import android.app.Application
import com.example.withpet.dao.CommentDao
import com.example.withpet.database.CommentDatabase
import com.example.withpet.entity.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentRepository(application: Application) {

    private val commentDao : CommentDao

    init {
        var db = CommentDatabase.getDatabase(application)
        commentDao = db.commentDao()
    }

    fun insert(comment : Comment) {
        CoroutineScope(Dispatchers.IO).launch {
            commentDao.insertComment(comment)
        }
    }

    fun delete(comment : Comment) {
        CoroutineScope(Dispatchers.IO).launch {
            commentDao.deleteComment(comment)
        }
    }

    fun getCommentList(boardId : Long, completed: (List<Comment>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(commentDao.getCommentList(boardId))
        }
    }
}