package com.example.withpet.dao

import androidx.room.*
import com.example.withpet.entity.HomeBoard

@Dao
interface HomeBoardDao {

    @Insert
    fun insertHomeBoard(board : HomeBoard)

    @Delete
    fun deleteHomeBoard(board : HomeBoard)

    @Update
    fun updateHomeBoard(vararg board: HomeBoard)

    @Query("select * from homeboard where region=:region and town=:town")
    fun homeboardData(region : String, town : String) : List<HomeBoard>

    @Query("select * from homeboard where id=:id")
    fun findById(id : Long) : HomeBoard


}