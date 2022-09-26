package com.example.withpet.viewModel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.HomeBoard
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.viewModel.HomeBoardViewModel

class HomeBoardDetailViewModel(private val repository: HomeBoardRepository) : ViewModel() {

    var progressVisible = MutableLiveData<Boolean>()
    var homeBoard = MutableLiveData<HomeBoard>()

    fun detail(boardId : Long) {
        progressVisible.postValue(true)
        repository.detail(boardId) {
            this.homeBoard.postValue(it)
            progressVisible.postValue(false)
        }
    }
}
class HomeBoardDetailViewModelFactory(private val repository: HomeBoardRepository) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeBoardDetailViewModel::class.java)) {
                return HomeBoardDetailViewModel(repository) as T
            }
            throw IllegalAccessException("Unkown Viewmodel Class")
        }
}