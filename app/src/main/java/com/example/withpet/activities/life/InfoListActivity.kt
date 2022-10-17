package com.example.withpet.activities.life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.R
import com.example.withpet.activities.ImageViewActivity
import com.example.withpet.adapter.InfoBoardRVAdapter
import com.example.withpet.databinding.ActivityInfoListBinding
import com.example.withpet.entity.InfoBoard
import com.example.withpet.repository.InfoBoardRepository
import com.example.withpet.repository.UserRepository
import com.example.withpet.utils.toVisibility
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.example.withpet.viewModel.life.InfoBoardListViewModel
import com.example.withpet.viewModel.life.InfoBoardListViewModelFactory

class InfoListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInfoListBinding
    private lateinit var adapter : InfoBoardRVAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var infoViewModel : InfoBoardListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_list)

        val userRepository = UserRepository(this.application)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(
            UserViewModel::class.java
        )

        val infoRepository = InfoBoardRepository(this.application)
        infoViewModel = ViewModelProvider(this, InfoBoardListViewModelFactory(infoRepository)).get(
            InfoBoardListViewModel::class.java
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        adapter = InfoBoardRVAdapter()
            .apply { onClick = this@InfoListActivity::startImgActivity }
            .apply { onBoardClick = this@InfoListActivity::startDetailActivity }

        binding.infoBoardRv.adapter = adapter

        getListData()

        infoViewModel.progressVisible.observe(this) {
            binding.progressBar.visibility = it.toVisibility()
        }

        binding.swipe.setOnRefreshListener {
            getListData()
            binding.swipe.isRefreshing = false
        }
    }
    private fun startDetailActivity(infoBoard: InfoBoard) {
        startActivity(Intent(this, InfoDetailActivity::class.java)
            .apply {
                putExtra(InfoDetailActivity.INFO_BOARD_ID, infoBoard.id)
            })
    }
    private fun startImgActivity( infoBoard: InfoBoard) {
        startActivity(Intent(this, ImageViewActivity::class.java)
                .apply {
                    putExtra(ImageViewActivity.INFO_BOARD_ID, infoBoard.id)
                })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == 200) {
            getListData()
        }
    }

    private fun getListData() {
        userViewModel._mutableRegion.observe(this) { region ->
            userViewModel._mutableTown.observe(this) { town ->
                infoViewModel.list(region, town!!)
            }
        }

        infoViewModel.infoBoardList.observe(this) {
            Log.d("withBoard", it.toString())
            adapter.updateList(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.withboard_toolbar_item, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.writeBtn -> {
                val intent = Intent(this, InfoWriteActivity::class.java)
                startActivityForResult(intent, 200)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}