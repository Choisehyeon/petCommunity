package com.example.withpet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.withpet.entity.Comment

@Dao
interface CommentDao {

    @Insert
    fun insertComment(comment : Comment)

    @Delete
    fun deleteComment(comment : Comment)

    @Query("select * from comment where board_id =:board_id")
    fun getCommentList(board_id : Long) : List<Comment>
}