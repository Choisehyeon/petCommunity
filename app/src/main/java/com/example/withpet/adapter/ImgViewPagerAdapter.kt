package com.example.withpet.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.withpet.R
import com.example.withpet.entity.InfoBoard
import com.example.withpet.entity.InfoImage

class ImgViewPagerAdapter : RecyclerView.Adapter<ImgViewPagerAdapter.PagerViewHolder>() {

    var item = mutableListOf<String>()

    class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.imgslide_rv_item, parent, false)) {

        val img = itemView.findViewById<ImageView>(R.id.imgView)
        fun bindItems(image : String) {
            val encodeByte = Base64.decode(image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)

            Glide.with(itemView)
                .load(bitmap)
                .into(img)
        }
    }

    fun updateList(imgList : List<String>) {
        this.item.clear()
        this.item.addAll(imgList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImgViewPagerAdapter.PagerViewHolder = PagerViewHolder((parent))

    override fun onBindViewHolder(holder: ImgViewPagerAdapter.PagerViewHolder, position: Int) {
        holder.bindItems(item[position])
    }



    override fun getItemCount(): Int {
        return item.size
    }


}