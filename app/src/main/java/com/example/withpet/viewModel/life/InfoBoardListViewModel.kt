package com.example.withpet.viewModel.life

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.InfoBoard
import com.example.withpet.entity.InfoImage
import com.example.withpet.repository.InfoBoardRepository

class InfoBoardListViewModel(private val repository : InfoBoardRepository) : ViewModel() {

    val progressVisible = MutableLiveData<Boolean>()
    var infoBoardList = MutableLiveData<List<InfoBoard>>()
    var infoBoard = MutableLiveData<InfoBoard>()
    var imgList = MutableLiveData<List<String>>()

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

    fun updateImgList(img_List : List<String>, id : Long) {
        repository.updateImgList(img_List, id)
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