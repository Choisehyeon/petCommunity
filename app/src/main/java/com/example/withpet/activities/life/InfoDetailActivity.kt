package com.example.withpet.activities.life

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.withpet.R
import com.example.withpet.activities.ImageViewActivity
import com.example.withpet.adapter.InfoCommentRVAdapter
import com.example.withpet.databinding.ActivityInfoDetailBinding
import com.example.withpet.entity.Comment
import com.example.withpet.entity.InfoBoard
import com.example.withpet.repository.CommentRepository
import com.example.withpet.repository.InfoBoardRepository
import com.example.withpet.repository.UserRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.example.withpet.viewModel.life.InfoBoardDetailViewModel
import com.example.withpet.viewModel.life.InfoBoardDetailViewModelFactory
import com.example.withpet.viewModel.life.InfoCommentViewModel
import com.example.withpet.viewModel.life.InfoCommentViewModelFactory

class InfoDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInfoDetailBinding
    private lateinit var userViewModel : UserViewModel
    private lateinit var viewModel : InfoBoardDetailViewModel
    private lateinit var commentViewModel : InfoCommentViewModel
    private lateinit var adapter : InfoCommentRVAdapter

    companion object {
        const val INFO_BOARD_ID = "board_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_detail)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val userRepository = UserRepository(this.application)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(
            UserViewModel::class.java
        )

        val repository = InfoBoardRepository(this.application)
        viewModel = ViewModelProvider(this, InfoBoardDetailViewModelFactory(repository)).get(
            InfoBoardDetailViewModel::class.java
        )

        val commentRepository = CommentRepository(this.application)
        commentViewModel = ViewModelProvider(this, InfoCommentViewModelFactory(commentRepository)).get(
            InfoCommentViewModel::class.java
        )

        val boardId = intent.getLongExtra(INFO_BOARD_ID, 0)
        if (boardId == null) {
            finish()
            return
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        adapter = InfoCommentRVAdapter()
        binding.commentRV.adapter = adapter

        getCommentData(boardId)

        viewModel.detail(boardId)
        viewModel.infoBoard.observe(this, this::setDetailInfo)

        binding.infoImgArea.setOnClickListener {
            startImgViewAcitivty(boardId)
        }

        binding.chatBtn.setOnClickListener {
            binding.chatEdit.requestFocus()
            var imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.chatEdit, 0)
        }

        binding.detailArea.setOnClickListener {
            var imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.chatEdit.windowToken, 0)
        }

        binding.chatEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.chatUpBtn.visibility = View.VISIBLE
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        userViewModel.getUser(FBAuth.getUid())
        binding.chatUpBtn.setOnClickListener {
            insertComment(boardId)
            binding.chatEdit.setText("")
            imm.hideSoftInputFromWindow(binding.chatEdit.windowToken, 0)
            binding.chatUpBtn.visibility = View.INVISIBLE
        }
    }

    fun insertComment(boardId : Long) {
        userViewModel._mutableUser.observe(this) {
            commentViewModel.insert(Comment(0, boardId, it, FBAuth.getTime(), binding.chatEdit.text.toString()))
        }
    }

    private fun getCommentData(boardId : Long) {
        commentViewModel.getCommentList(boardId)

        commentViewModel.commentList.observe(this) {
            adapter.updateList(it)
            adapter.notifyDataSetChanged()
        }
    }

    fun startImgViewAcitivty(id : Long) {
        startActivity(
            Intent(this, ImageViewActivity::class.java)
            .apply {
                putExtra(ImageViewActivity.INFO_BOARD_ID, id)
            })
    }

    fun setDetailInfo(board: InfoBoard) {
        userViewModel.getUser(board.board_uid)

        userViewModel._mutableUser.observe(this) {
            Glide.with(this)
                .load(it.profile)
                .apply(RequestOptions().circleCrop())
                .into(binding.userImg)
            binding.nickname.text = it.nickname
            binding.place.text = it.town
        }

        binding.time.text = board.write_time
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

            Glide.with(this)
                .load(bitmap)
                .into(binding.infoImg)

            if (board.imgList!!.size > 1) {
                binding.imgNum.visibility = View.VISIBLE
                binding.imgNum.text = "+${board.imgList!!.size-1}"
            } else {
                binding.imgNum.visibility = View.GONE
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