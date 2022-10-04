package com.example.withpet.viewModel.life

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.WithBoard
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.repository.WithBoardRepository
import com.example.withpet.viewModel.HomeBoardViewModel

class WithBoardListViewModel(private val repository: WithBoardRepository) : ViewModel() {

    val progressVisible = MutableLiveData<Boolean>()
    var withBoardList = MutableLiveData<List<WithBoard>>()
    var withBoard = MutableLiveData<WithBoard>()

    var limitPeople = MutableLiveData(3)

    fun insert(board : WithBoard) {
        repository.insert(board)
    }

    fun list(region : String, town : String) {
        progressVisible.postValue(true)
        repository.getWithBoardData(region, town) {
            this.withBoardList.postValue(it)
            progressVisible.postValue(false)
        }
    }

    fun plusPeople() {
        limitPeople.value = limitPeople.value!!.plus(1)
    }
    fun minusPeople() {
        limitPeople.value = limitPeople.value!!.minus(1)
    }

    fun getPeople() : Int{
        return limitPeople.value!!
    }

}

class WithBoardListViewModelFactory(private val repository: WithBoardRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WithBoardListViewModel::class.java)) {
            return WithBoardListViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}