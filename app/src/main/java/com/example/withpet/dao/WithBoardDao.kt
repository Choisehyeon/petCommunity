package com.example.withpet.dao

import androidx.room.*
import com.example.withpet.entity.WithBoard

@Dao
interface WithBoardDao {

    @Insert
    fun insertWithBoard(board: WithBoard)

    @Delete
    fun deleteWithBoard(board : WithBoard)

    @Update
    fun updateWithBoard(board : WithBoard)

    @Query("select * from withboard where region=:region and town =:town" )
    fun withBoardData(region : String, town : String) : List<WithBoard>

    @Query("select * from withboard where id=:id")
    fun findById(id : Long) : WithBoard
}