package com.example.withpet.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.databinding.HomeBoardRvItemBinding
import com.example.withpet.entity.HomeBoard

class HomeBoardRVAdapter : RecyclerView.Adapter<
HomeBoardRVAdapter.ViewHolder>() {

    private val homeBoards = mutableListOf<HomeBoard>()
    var onClick : (HomeBoard) -> Unit = {}

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = HomeBoardRvItemBinding.bind(itemView)
        fun bindItems(board: HomeBoard) {

            binding.boardTitle.text = board.title
            binding.boardPrice.text = board.price
            binding.boardTime.text = board.time
            binding.boardPlace.text = board.town
            binding.boardImg.setImageBitmap(board.image)

        }
    }

    fun updatesList(homeBoards : List<HomeBoard>) {
        this.homeBoards.clear()
        this.homeBoards.addAll(homeBoards.reversed())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBoardRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_board_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return homeBoards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeBoard = homeBoards[position]
        holder.bindItems(homeBoard)
        holder.itemView.setOnClickListener { onClick(homeBoard) }
    }
}