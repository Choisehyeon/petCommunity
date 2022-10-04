package com.example.withpet.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.databinding.ImgboardRvItemBinding

class ImageRVAdapter(val list : ArrayList<Bitmap>) :RecyclerView.Adapter<ImageRVAdapter.ViewHolder>(){

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val binding = ImgboardRvItemBinding.bind(itemView)

        fun bindItems(image : Bitmap) {
            binding.infoImg.setImageBitmap(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.imgboard_rv_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageRVAdapter.ViewHolder, position: Int) {
        val image = list[position]
        holder.bindItems(image)
        holder.binding.removeBtn.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}