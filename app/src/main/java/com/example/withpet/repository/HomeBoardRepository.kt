package com.example.withpet.repository

import android.app.Application
import com.example.withpet.dao.HomeBoardDao
import com.example.withpet.database.HomeBoardDatabase
import com.example.withpet.entity.HomeBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeBoardRepository(application: Application) {
    private val homeBoardDao : HomeBoardDao

    init {
        var db = HomeBoardDatabase.getDatabase(application)
        homeBoardDao = db.HomeBoardDao()
    }

    fun insert(board : HomeBoard) {
        CoroutineScope(Dispatchers.IO).launch {
            homeBoardDao.insertHomeBoard(board)
        }
    }

    fun getHomeBoardData(region : String, town : String, completed: (List<HomeBoard>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(homeBoardDao.homeboardData(region, town))
        }
    }
}