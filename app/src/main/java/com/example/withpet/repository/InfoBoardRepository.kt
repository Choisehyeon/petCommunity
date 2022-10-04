package com.example.withpet.repository

import android.app.Application
import com.example.withpet.dao.InfoBoardDao
import com.example.withpet.database.InfoBoardDatabase
import com.example.withpet.entity.InfoBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoBoardRepository(application: Application) {

    private val infoBoardDao : InfoBoardDao

    init{
        var db = InfoBoardDatabase.getDatabase(application)
        infoBoardDao = db.infoBoardDao()
    }

    fun insert(board: InfoBoard) {
        CoroutineScope(Dispatchers.IO).launch {
            infoBoardDao.insertInfoBoard(board)
        }
    }

    fun detail(id : Long, completed: (InfoBoard) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(infoBoardDao.findById(id))
        }
    }

    fun getInfoBoardData(region: String, town : String, completed: (List<InfoBoard>) -> Unit) {
        completed(infoBoardDao.infoBoardData(region, town))
    }
}