package com.example.withpet.repository

import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import com.example.withpet.MainActivity
import com.example.withpet.activities.member.LoginActivity
import com.example.withpet.dao.UserDao
import com.example.withpet.database.UserDatabase
import com.example.withpet.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class UserRepository(application: Application) {
    private val userDao : UserDao

    private lateinit var auth : FirebaseAuth
    val loginLoading = MutableLiveData<Boolean>()
    val joinLoading = MutableLiveData<Boolean>()

    init {
        var db = UserDatabase.getDatabase(application)
        auth = Firebase.auth
        userDao = db!!.userDao()
    }

    fun insert(context: Context, email : String, password: String, nickname: String) {
        joinLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                joinLoading.value = false
                CoroutineScope(Dispatchers.IO).launch {
                    userDao.insertUser(User(auth.currentUser!!.uid, nickname, "", ""))
                }
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }.addOnFailureListener {
                joinLoading.value = false
            }

    }
    fun login(context: Context, email: String, password: String) {
        loginLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            println("Login Success")
            loginLoading.value = false
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

        }.addOnFailureListener {
            println("Login Error")
            loginLoading.value = false
        }
    }

    fun delete(user : User) {
        userDao.deleteUser(user)
    }

    fun updateAddress(context: Context, town: String, region: String, uid: String) {
        CoroutineScope(Dispatchers.IO).launch{
            userDao.updateAddress(town, region, uid)
        }
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)

    }

    suspend fun getTownByUid(uid : String) : String =
        withContext(Dispatchers.IO) {
            return@withContext userDao.getTownByUid(uid)
        }

    suspend fun getNicknameByUid(uid : String) : String{
        return userDao.getNicknameByUid(uid).toString()
    }


}