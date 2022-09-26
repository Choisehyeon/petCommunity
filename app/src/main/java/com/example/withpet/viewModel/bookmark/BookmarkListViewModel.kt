package com.example.withpet.viewModel.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.Bookmark
import com.example.withpet.repository.BookmarkRepository
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.viewModel.HomeBoardViewModel

class BookmarkListViewModel(private val repository: BookmarkRepository) : ViewModel() {

    var progressVisible = MutableLiveData<Boolean>()
    var bookmarkList = MutableLiveData<List<Bookmark>>()
    var bookmarkIdList = MutableLiveData<List<Long>>()

    fun insert(bookmark: Bookmark) {
        repository.insert(bookmark)
    }

    fun delete(bookmark: Bookmark) {
        repository.delete(bookmark)
    }

    fun getBookmarkList(uid : String) {
        progressVisible.postValue(true)
        repository.getBookmarkList(uid) {
            bookmarkList.postValue(it)
            progressVisible.postValue(false)
        }
    }

    fun getBookmarkIdList() {
        repository.getBookmarkIdList {
            bookmarkIdList.postValue(it)
        }
    }
}


class BookmarkListViewModelFactory(private val repository: BookmarkRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkListViewModel::class.java)) {
            return BookmarkListViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}