package com.example.withpet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.entity.Bookmark

class BookmarkRVAdapter : RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>() {

    private val bookmarkList = mutableListOf<Bookmark>()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

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
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }
}