package com.example.withpet.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.withpet.R
import com.example.withpet.databinding.InfoboardRvItemBinding
import com.example.withpet.entity.InfoBoard
import com.example.withpet.entity.InfoImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI
import java.util.logging.Level.parse

class InfoBoardRVAdapter: RecyclerView.Adapter<InfoBoardRVAdapter.ViewHolder>() {

    private val infoBoards = mutableListOf<InfoBoard>()
    var onClick : (InfoBoard) -> Unit = {}
    var onBoardClick : (InfoBoard) -> Unit = {}

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = InfoboardRvItemBinding.bind(itemView)

        fun bindItems(board : InfoBoard) {
            Log.d("board", board.toString())
            binding.infoContent.text = board.content

            if(!board.place.equals("")) {
                binding.infoPlaceArea.visibility = View.VISIBLE
                binding.infoPlace.text = board.place
            } else {
                binding.infoPlaceArea.visibility = View.GONE
            }

            if(board.imgList!!.size == 0 || board.imgList == null) {
                binding.infoImgArea.visibility = View.GONE
                binding.imgNum.visibility = View.GONE
            } else {
                binding.infoImgArea.visibility = View.VISIBLE
                val encodeByte = Base64.decode(board.imgList!![0], Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)

                Glide.with(itemView)
                    .load(bitmap)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
                    .into(binding.infoImgArea)

                if (board.imgList!!.size > 1) {
                    binding.imgNum.visibility = View.VISIBLE
                    binding.imgNum.text = "+${board.imgList!!.size-1}"
                } else {
                    binding.imgNum.visibility = View.GONE
                }
            }

            binding.nickName.text = board.nickname
            binding.townName.text = board.town
            binding.writeTime.text = board.write_time
        }

    }

    fun updateList(infoBoards : List<InfoBoard>) {
        this.infoBoards.clear()
        this.infoBoards.addAll(infoBoards.reversed())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoBoardRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.infoboard_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoBoardRVAdapter.ViewHolder, position: Int) {
        val infoBoard = infoBoards[position]
        holder.bindItems(infoBoard)
        holder.itemView.findViewById<ImageView>(R.id.infoImgArea).setOnClickListener {
            onClick(infoBoard)
        }
        holder.itemView.setOnClickListener {
            onBoardClick(infoBoard)
        }
    }

    override fun getItemCount(): Int {
        return infoBoards.size
    }
}