package com.example.withpet.viewModel.life

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.InfoBoard
import com.example.withpet.repository.InfoBoardRepository


class InfoBoardDetailViewModel(private val repository: InfoBoardRepository) : ViewModel() {
    var infoBoard = MutableLiveData<InfoBoard>()

    fun detail(boardId : Long) {
        repository.detail(boardId) {
            this.infoBoard.postValue(it)
        }
    }
}

class InfoBoardDetailViewModelFactory(private val repository: InfoBoardRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoBoardDetailViewModel::class.java)) {
            return InfoBoardDetailViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}