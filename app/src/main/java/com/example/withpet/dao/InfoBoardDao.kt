package com.example.withpet.dao

import androidx.room.*
import com.example.withpet.entity.InfoBoard
import com.example.withpet.entity.InfoImage

@Dao
interface InfoBoardDao {

    @Insert
    fun insertInfoBoard(board : InfoBoard)

    @Delete
    fun deleteInfoBoard(board : InfoBoard)

    @Update
    fun updateInfoBoard(board : InfoBoard)

    @Query("update infoboard set img_list =:imgList where board_uid=:id ")
    fun updateImgList(imgList : List<String>, id : Long)

    @Query("select * from infoboard where region=:region and town=:town")
    fun infoBoardData(region : String, town : String) : List<InfoBoard>

    @Query("select * from infoboard where id=:id")
    fun findById(id : Long) : InfoBoard



}