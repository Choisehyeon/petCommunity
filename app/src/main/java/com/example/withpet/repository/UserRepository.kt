package com.example.withpet.repository

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import com.example.withpet.MainActivity
import com.example.withpet.R
import com.example.withpet.activities.CheckAreaActivity
import com.example.withpet.activities.member.LoginActivity
import com.example.withpet.dao.UserDao
import com.example.withpet.database.UserDatabase
import com.example.withpet.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class UserRepository(application: Application) {
    private val userDao: UserDao

    private lateinit var auth: FirebaseAuth

    init {
        var db = UserDatabase.getDatabase(application)
        auth = Firebase.auth
        userDao = db!!.userDao()
    }

    fun insert(context: Context, email: String, password: String, nickname: String) {

        val profile = BitmapFactory.decodeResource(context.resources, R.drawable.profile)

        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertUser(User(auth.currentUser!!.uid, nickname, "", "", profile))
        }

    }


    fun delete(user: User) {
        userDao.deleteUser(user)
    }

    fun updateAddress(context: Context, town: String, region: String, uid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.updateAddress(town, region, uid)
        }
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)

    }

    fun getTownByUid(uid: String, completed: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(userDao.getTownByUid(uid))
        }
    }

    fun getRegionByUid(uid: String, completed: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(userDao.getRegionByUid(uid))
        }
    }

    fun getNicknameList(completed: (List<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(userDao.getNickNameList())
        }
    }

    fun getNicknameByUid(uid: String, completed : (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(userDao.getNicknameByUid(uid))
        }
    }

    fun getUser(uid : String, completed: (User) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            completed(userDao.getUser(uid))
        }
    }


}