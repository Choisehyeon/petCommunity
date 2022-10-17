package com.example.withpet.adapter

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.activities.life.InfoWriteActivity
import com.example.withpet.databinding.ImgboardRvItemBinding

class ImageRVAdapter(val list : ArrayList<Uri>) :RecyclerView.Adapter<ImageRVAdapter.ViewHolder>(){

    var onClick : (ArrayList<Uri>) -> Unit = {}
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val binding = ImgboardRvItemBinding.bind(itemView)

        fun bindItems(image : Uri) {
            binding.infoImg.setImageURI(image)
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
            list.remove(image)
            onClick(list)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}