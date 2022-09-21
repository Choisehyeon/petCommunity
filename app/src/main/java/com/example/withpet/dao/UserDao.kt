package com.example.withpet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.withpet.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user : User)

    @Delete
    fun deleteUser(user : User)

    @Query("select nickname from User where uid= :uid")
    suspend fun getNicknameByUid(uid : String) : String

    @Query("Update User Set town=:town, region =:region Where uid=:uid")
    suspend fun updateAddress(town : String, region : String, uid: String)

    @Query("select town from User where uid= :uid")
    suspend fun getTownByUid(uid : String) : String

}