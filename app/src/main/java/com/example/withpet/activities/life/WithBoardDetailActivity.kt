package com.example.withpet.activities.life

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.CalendarContract
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.withpet.R
import com.example.withpet.activities.home.HomeBoardActivity
import com.example.withpet.databinding.ActivityWithBoardDetailBinding
import com.example.withpet.entity.WithBoard
import com.example.withpet.repository.UserRepository
import com.example.withpet.repository.WithBoardRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.example.withpet.viewModel.life.WithBoardDetailViewModel
import com.example.withpet.viewModel.life.WithBoardDetailViewModelFactory

class WithBoardDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWithBoardDetailBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var withViewModel : WithBoardDetailViewModel
    var participantsImgList = mutableListOf<Bitmap>()

    companion object {
        const val DETAIL_WITHBOARD_ID = "withBoardId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_with_board_detail)

        val userRepository = UserRepository(this.application)
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        ).get(UserViewModel::class.java)

        val withBoardRepository = WithBoardRepository(this.application)
        withViewModel = ViewModelProvider(
            this,
            WithBoardDetailViewModelFactory(withBoardRepository)
        ).get(WithBoardDetailViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        val boardId = intent.getLongExtra(WithBoardDetailActivity.DETAIL_WITHBOARD_ID, 0)
        if (boardId == null) {
            finish()
            return
        }

        withViewModel.detail(boardId)

        withViewModel.withBoard.observe(this) {
            if(it.board_uid.equals(FBAuth.getUid())) {
                binding.intoBtnArea.visibility = View.INVISIBLE
            } else {
                binding.intoBtnArea.visibility = View.VISIBLE
            }
        }
        withViewModel.withBoard.observe(this, this::setDetailInfo)
    }

    private fun setDetailInfo(board: WithBoard){
        var list = mutableListOf<String>()
        userViewModel.getUser(board.board_uid)

        userViewModel._mutableUser.observe(this) {
            Glide.with(this)
                .load(it.profile)
                .apply(RequestOptions().circleCrop())
                .into(binding.userImg)

            binding.nickname.text = it.nickname
        }

        if(board.participants?.size == board.people) {
            binding.possibleParticipant.text = "모집완료"
        } else {
            binding.possibleParticipant.text = "모집중"
        }
        binding.wbTitle.text = board.title
        binding.possible.text = board.age + board.gender
        binding.date.text = board.date + " " + board.time
        binding.meetPlace.text = board.place
        binding.wbContent.text = board.content
        if(board.participants == null) {
            binding.intoPerson.text = "0"
        } else {
            binding.intoPerson.text = board.participants?.size.toString()
        }
        binding.allPerson.text = board.people.toString()

        if((board.participants != null && board.participants.contains(FBAuth.getUid())) || board.board_uid.equals(FBAuth.getUid())) {
            binding.intoBtn.isEnabled = false
            binding.intoBtn.setBackgroundColor(Color.GRAY)
        } else {
            binding.intoBtn.isEnabled = true
        }
        if(board.participants != null) {
            getParticipantsImgList(board.participants!!)
            Log.d("withBoard", participantsImgList.toString())
        }

        binding.intoBtn.setOnClickListener {
            list = board.participants as MutableList<String>
            list.add(FBAuth.getUid())

            withViewModel.updateParticipants(board.id, list)
        }
    }

    private fun getParticipantsImgList(participants : List<String>) {
        participants.forEach {
            userViewModel.getUser(it)

            userViewModel._mutableUser.observe(this) {
                participantsImgList.add(it.profile!!)
            }
        }
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