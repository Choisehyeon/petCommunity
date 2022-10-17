package com.example.withpet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.databinding.CommentRvItemBinding
import com.example.withpet.entity.Comment

class InfoCommentRVAdapter : RecyclerView.Adapter<InfoCommentRVAdapter.ViewHolder>() {

    private val commentList = mutableListOf<Comment>()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = CommentRvItemBinding.bind(itemView)

        fun bindItems(comment : Comment) {
            binding.profileImg.setImageBitmap(comment.user.profile)
            binding.nickName.text = comment.user.nickname
            binding.townName.text = comment.user.town
            binding.writeTime.text = comment.time
            binding.comment.text = comment.comment
        }
    }

    fun updateList(commentList : List<Comment>) {
        this.commentList.clear()
        this.commentList.addAll(commentList.reversed())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoCommentRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoCommentRVAdapter.ViewHolder, position: Int) {
        val comment = commentList[position]
        holder.bindItems(comment)

    }

    override fun getItemCount(): Int {
        return commentList.size
    }


}