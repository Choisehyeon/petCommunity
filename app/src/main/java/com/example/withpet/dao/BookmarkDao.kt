package com.example.withpet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.withpet.entity.Bookmark

@Dao
interface BookmarkDao {

    @Insert
    fun insert(bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark)

    @Query("select * From bookmark where userId = :userId")
    fun getBookmarkList(userId : String) : List<Bookmark>

    @Query("select id From bookmark where userId =:userId")
    fun getBookmarkIdList(userId : String) : List<String>
}