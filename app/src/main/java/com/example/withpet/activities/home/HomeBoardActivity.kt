package com.example.withpet.activities.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.withpet.R
import com.example.withpet.databinding.ActivityHomeBoardBinding
import com.example.withpet.entity.Bookmark
import com.example.withpet.entity.HomeBoard
import com.example.withpet.repository.BookmarkRepository
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.repository.UserRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.utils.toVisibility
import com.example.withpet.viewModel.HomeBoardViewModel
import com.example.withpet.viewModel.HomeBoardViewModelFactory
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.example.withpet.viewModel.bookmark.BookmarkListViewModel
import com.example.withpet.viewModel.bookmark.BookmarkListViewModelFactory
import com.example.withpet.viewModel.home.HomeBoardDetailViewModel
import com.example.withpet.viewModel.home.HomeBoardDetailViewModelFactory

class HomeBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBoardBinding
    private lateinit var viewModel: HomeBoardDetailViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var bookmarkViewModel: BookmarkListViewModel

    companion object {
        const val DETAIL_BOARD_ID = "boardId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_board)

        val boardRepository = HomeBoardRepository(this.application)
        viewModel = ViewModelProvider(this, HomeBoardDetailViewModelFactory(boardRepository)).get(
            HomeBoardDetailViewModel::class.java
        )

        val userRepository = UserRepository(this.application)
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        ).get(UserViewModel::class.java)

        val bookmarkRepository = BookmarkRepository(this.application)
        bookmarkViewModel =
            ViewModelProvider(this, BookmarkListViewModelFactory(bookmarkRepository)).get(
                BookmarkListViewModel::class.java
            )

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit_bold)

        bookmarkViewModel.getBookmarkIdList(FBAuth.getUid())

        val boardId = intent.getLongExtra(DETAIL_BOARD_ID, 0)
        if (boardId == null) {
            finish()
            return
        }

        viewModel.homeBoard.observe(this, this::setDetail)
        viewModel.progressVisible.observe(this) { visible ->
            binding.progressBar.visibility = visible.toVisibility()
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        viewModel.detail(boardId)
        val key = FBAuth.getUid() + boardId

        bookmarkViewModel.bookmarkIdList.observe(this) { bookmarkList ->
            var bookmark: MutableList<String> = bookmarkList as MutableList<String>

            if (bookmark.contains(key)) {
                binding.bookmarkBtn.setImageResource(R.drawable.bookmark_on)
            } else {
                binding.bookmarkBtn.setImageResource(R.drawable.bookmark_off)
            }

            binding.bookmarkBtn.setOnClickListener {
                viewModel.homeBoard.observe(this) {
                    if (bookmark.contains(key)) {
                        bookmark.remove(key)
                        bookmarkViewModel.delete(Bookmark(key, FBAuth.getUid(),  it))
                        binding.bookmarkBtn.setImageResource(R.drawable.bookmark_off)

                    } else {
                        bookmark.add(key)
                        bookmarkViewModel.insert(Bookmark(key, FBAuth.getUid(),  it))
                        binding.bookmarkBtn.setImageResource(R.drawable.bookmark_on)
                    }
                }

            }
        }


    }

    private fun setDetail(board: HomeBoard) {

        userViewModel.getUser(board.uid)

        binding.boardImg.setImageBitmap(board.image)

        userViewModel._mutableUser.observe(this) {
            Glide.with(this)
                .load(it.profile)
                .apply(RequestOptions().circleCrop())
                .into(binding.userImg)
            binding.nickname.text = it.nickname
        }

        binding.place.text = board.town
        binding.boardTitle.text = board.title
        binding.date.text = board.time
        binding.boardContent.text = board.content
        binding.price.text = board.price + "원"
    }
}