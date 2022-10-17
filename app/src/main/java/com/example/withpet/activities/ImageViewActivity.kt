package com.example.withpet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.withpet.R
import com.example.withpet.adapter.ImgViewPagerAdapter
import com.example.withpet.databinding.ActivityImageViewBinding
import com.example.withpet.repository.InfoBoardRepository
import com.example.withpet.viewModel.life.*

class ImageViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityImageViewBinding
    private lateinit var viewModel : InfoBoardDetailViewModel
    private lateinit var adapter: ImgViewPagerAdapter

    companion object {
        const val INFO_BOARD_ID = "info_board_Id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_view)

        var infoBoardRepository = InfoBoardRepository(this.application)
        viewModel = ViewModelProvider(this, InfoBoardDetailViewModelFactory(infoBoardRepository)).get(
            InfoBoardDetailViewModel::class.java
        )

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        val id = intent.getLongExtra(INFO_BOARD_ID, 0)
        if(id == null) {
            finish()
            return
        }

        viewModel.detail(id)
        viewModel.infoBoard.observe(this) {
            adapter.updateList(it.imgList!!)
        }

        adapter = ImgViewPagerAdapter()
        binding.imageViewPager.adapter = adapter
        binding.imageViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager2(binding.imageViewPager)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}