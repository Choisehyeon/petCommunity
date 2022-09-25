package com.example.withpet.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.HomeBoard
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.repository.UserRepository

class HomeBoardViewModel(private val repository: HomeBoardRepository) : ViewModel() {


    var progressVisible = MutableLiveData<Boolean>()
    var lectureList = MutableLiveData<List<HomeBoard>>()


    fun insert(board: HomeBoard) {
        repository.insert(board)
    }

    fun list(region : String, town : String) {
        progressVisible.postValue(true)
        repository.getHomeBoardData(region, town) {
            this.lectureList.postValue(it)
            progressVisible.postValue(false)
        }
    }


}

class HomeBoardViewModelFactory(private val repository: HomeBoardRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeBoardViewModel::class.java)) {
            return HomeBoardViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}