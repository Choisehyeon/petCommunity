package com.example.withpet.repository

import android.app.Application
import com.example.withpet.dao.WithBoardDao
import com.example.withpet.database.WithBoardDatabase
import com.example.withpet.entity.WithBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WithBoardRepository(application : Application) {
    private val withBoardDao : WithBoardDao

    init {
        var db = WithBoardDatabase.getDatabase(application)
        withBoardDao = db.WithBoardDao()
    }

    fun insert(board: WithBoard) {
        CoroutineScope(Dispatchers.IO).launch {
            withBoardDao.insertWithBoard(board)
        }
    }
    fun detail(id : Long, completed: (WithBoard) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(withBoardDao.findById(id))
        }
    }

    fun getWithBoardData(region: String, town : String, completed: (List<WithBoard>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(withBoardDao.withBoardData(region, town))
        }
    }
}