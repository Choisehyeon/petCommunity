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
import com.example.withpet.adapter.WithBoardRVAdapter
import com.example.withpet.databinding.ActivityWithBinding
import com.example.withpet.entity.WithBoard
import com.example.withpet.repository.UserRepository
import com.example.withpet.repository.WithBoardRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.utils.toVisibility
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.example.withpet.viewModel.life.WithBoardListViewModel
import com.example.withpet.viewModel.life.WithBoardListViewModelFactory

class WithActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWithBinding
    private lateinit var adapter : WithBoardRVAdapter
    private lateinit var userViewModel : UserViewModel
    private lateinit var withBoardViewModel : WithBoardListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_with)

        val userRepository = UserRepository(this.application)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(
            UserViewModel::class.java
        )

        val wbRepository = WithBoardRepository(this.application)
        withBoardViewModel = ViewModelProvider(this, WithBoardListViewModelFactory(wbRepository)).get(
            WithBoardListViewModel::class.java
        )


        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        adapter = WithBoardRVAdapter()
            .apply { onClick = this@WithActivity::startWithBoardActivity}
        binding.withBoardRv.adapter = adapter

        getListData()

        withBoardViewModel.progressVisible.observe(this) {
            binding.progressBar.visibility = it.toVisibility()
        }

        binding.swipe.setOnRefreshListener {
            getListData()
            binding.swipe.isRefreshing = false
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 200) {
            getListData()
        }


    }
    fun getListData() {
        userViewModel.getUser(FBAuth.getUid())
        userViewModel._mutableUser.observe(this) {
            withBoardViewModel.list(it.region, it.town)
        }

        withBoardViewModel.withBoardList.observe(this) {
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
                val intent = Intent(this, WithBoardWriteActivity::class.java)
                startActivityForResult(intent, 200)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun startWithBoardActivity(withBoard: WithBoard) {
        startActivity(
            Intent(this, WithBoardDetailActivity::class.java)
                .apply { putExtra(WithBoardDetailActivity.DETAIL_WITHBOARD_ID, withBoard.id) }
        )
    }
}