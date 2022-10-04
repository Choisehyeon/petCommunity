package com.example.withpet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.databinding.WithboardRvItemBinding
import com.example.withpet.entity.WithBoard

class WithBoardRVAdapter : RecyclerView.Adapter<WithBoardRVAdapter.ViewHolder>() {

    private val withBoardList = mutableListOf<WithBoard>()
    var onClick : (WithBoard) -> Unit = {}

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = WithboardRvItemBinding.bind(itemView)

        fun bindItems(withBoard: WithBoard) {
            binding.withBoardTitle.text = withBoard.title
            binding.withBoardContent.text = withBoard.content
            binding.possible.text = withBoard.age + withBoard.gender
            binding.date.text = withBoard.date + " " + withBoard.time
            if(withBoard.participants == null) {
                binding.currentP.text = "0"
            } else {
                binding.currentP.text = withBoard.participants?.size.toString()
            }
            binding.limitP.text = withBoard.people.toString()
            binding.nickName.text = withBoard.nickname
            binding.townName.text = withBoard.town
            binding.writeTime.text = withBoard.writeTime

        }
    }

    fun updateList(withBoards : List<WithBoard>) {
        this.withBoardList.clear()
        this.withBoardList.addAll(withBoards.reversed())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WithBoardRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.withboard_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WithBoardRVAdapter.ViewHolder, position: Int) {
        val withBoard = withBoardList[position]
        holder.bindItems(withBoard)
        holder.itemView.setOnClickListener { onClick(withBoard) }

    }

    override fun getItemCount(): Int {
        return withBoardList.size
    }


}