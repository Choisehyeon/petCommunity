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

    @Query("select * from User where uid= :uid")
    fun getUser(uid : String) : User

    @Query("Update User Set town=:town, region =:region Where uid=:uid")
    fun updateAddress(town : String, region : String, uid: String)

    @Query("select town from User where uid= :uid")
    fun getTownByUid(uid : String) : String

    @Query("select region from User where uid= :uid")
    fun getRegionByUid(uid : String) : String

    @Query("select nickname from User where uid=:uid")
    fun getNicknameByUid(uid: String) : String

    @Query("select nickname from User")
    fun getNickNameList() : List<String>

}