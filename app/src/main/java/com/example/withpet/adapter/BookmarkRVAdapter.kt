package com.example.withpet.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.withpet.R
import com.example.withpet.dao.HomeBoardDao
import com.example.withpet.database.HomeBoardDatabase
import com.example.withpet.databinding.BookmarkRvItemBinding
import com.example.withpet.entity.Bookmark
import com.example.withpet.entity.HomeBoard
import com.example.withpet.fragments.BookmarkFragment
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.viewModel.HomeBoardViewModel
import com.example.withpet.viewModel.HomeBoardViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookmarkRVAdapter: RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>() {

    private val bookmarkList = mutableListOf<Bookmark>()
    var onClick : (HomeBoard) -> Unit = {}
    var onRemoveClick : (Bookmark) -> Unit = {}


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = BookmarkRvItemBinding.bind(itemView)

        fun bindItems(bookmark : Bookmark) {

            Glide.with(itemView)
                .load(bookmark.board.image)
                .into(binding.boardImg)

            binding.boardTitle.text = bookmark.board.title
            binding.boardPlace.text = bookmark.board.town
            binding.boardTime.text = bookmark.board.time
            binding.boardPrice.text = bookmark.board.price + "원"

        }
    }

    fun updatesList(homeBoards : List<Bookmark>) {
        this.bookmarkList.clear()
        this.bookmarkList.addAll(homeBoards.reversed())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.ViewHolder, position: Int) {
        val bookmark = bookmarkList[position]
        holder.bindItems(bookmark)
        holder.itemView.setOnClickListener {
            onClick(bookmark.board)
        }
        holder.binding.bookmarkBtn.setOnClickListener {
            holder.binding.bookmarkBtn.setImageResource(R.drawable.bookmark_off)
            onRemoveClick(bookmark)
        }
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

}