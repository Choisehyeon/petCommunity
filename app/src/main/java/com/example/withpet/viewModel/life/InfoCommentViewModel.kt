package com.example.withpet.viewModel.life

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.entity.Comment
import com.example.withpet.repository.CommentRepository

class InfoCommentViewModel(private val repository: CommentRepository) : ViewModel() {

    var commentList = MutableLiveData<List<Comment>>()

    fun insert(comment : Comment) {
        repository.insert(comment)
    }

    fun delete(comment : Comment) {
        repository.delete(comment
        )
    }

    fun getCommentList(boardId : Long) {
        repository.getCommentList(boardId) {
            this.commentList.postValue(it)
        }
    }
}
class InfoCommentViewModelFactory(private val repository: CommentRepository) : ViewModelProvider.Factory {
    override fun<T : ViewModel> create(modelClass : Class<T>) : T {
        if(modelClass.isAssignableFrom(InfoCommentViewModel::class.java)) {
            return InfoCommentViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class")
    }
}