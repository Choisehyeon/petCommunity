package com.example.withpet.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.withpet.entity.User
import com.example.withpet.repository.UserRepository
import com.example.withpet.utils.FBAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val _mutableRegion = MutableLiveData<String>()
    val _mutableTown = MutableLiveData<String?>()
    val nicknameList = MutableLiveData<List<String>>()
    val _mutableUser = MutableLiveData<User>()

    init{
        getTownById(FBAuth.getUid())
        getRegionById(FBAuth.getUid())
    }

    fun join(context: Context, email: String, password: String, nickname : String) {
        repository.insert(context, email, password, nickname)

    }
    fun getUser(uid : String) {
        repository.getUser(uid) {
            _mutableUser.postValue(it)
        }
    }

    fun delete(user : User) {
        repository.delete(user)
    }

    fun getTownById(uid : String) {
        repository.getTownByUid(uid) {
            _mutableTown.postValue(it)
        }
    }

    fun getRegionById(uid : String) {
        repository.getRegionByUid(uid) {
            _mutableRegion.postValue(it)
        }
    }

    fun getNicknameList() {
        repository.getNicknameList {
            nicknameList.postValue(it)
        }
    }

    fun updateAddress(context: Context, uid: String) {
        repository.updateAddress(context, _mutableTown.value!!, _mutableRegion.value!!, uid)
    }
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}