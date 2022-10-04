package com.example.withpet.viewModel.life

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.WithBoard
import com.example.withpet.repository.WithBoardRepository

class WithBoardDetailViewModel(private val repository: WithBoardRepository) : ViewModel() {
    var progressVisible = MutableLiveData<Boolean>()
    var withBoard = MutableLiveData<WithBoard>()

    fun detail(boardId : Long) {
        progressVisible.postValue(true)
        repository.detail(boardId) {
            this.withBoard.postValue(it)
            progressVisible.postValue(false)
        }
    }
}
class WithBoardDetailViewModelFactory(private val repository: WithBoardRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WithBoardDetailViewModel::class.java)) {
            return WithBoardDetailViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}