package com.example.withpet.viewModel.life

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.InfoBoard
import com.example.withpet.repository.InfoBoardRepository

class InfoBoardListViewModel(private val repository : InfoBoardRepository) : ViewModel() {

    val progressVisible = MutableLiveData<Boolean>()
    var infoBoardList = MutableLiveData<List<InfoBoard>>()
    var infoBoard = MutableLiveData<InfoBoard>()

    fun insert(board : InfoBoard) {
        repository.insert(board)
    }

    fun list(region : String, town : String) {
        progressVisible.postValue(true)
        repository.getInfoBoardData(region, town) {
            this.infoBoardList.postValue(it)
            progressVisible.postValue(false)
        }
    }
}
class InfoBoardListViewModelFactory(private val repository: InfoBoardRepository) : ViewModelProvider.Factory {
    override fun<T : ViewModel> create(modelClass : Class<T>) : T {
        if(modelClass.isAssignableFrom(InfoBoardListViewModel::class.java)){
            return InfoBoardListViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}