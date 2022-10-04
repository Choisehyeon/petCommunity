package com.example.withpet.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.withpet.R
import com.example.withpet.activities.home.HomeBoardActivity
import com.example.withpet.adapter.BookmarkRVAdapter
import com.example.withpet.databinding.FragmentBookmarkBinding
import com.example.withpet.entity.HomeBoard
import com.example.withpet.repository.BookmarkRepository
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.viewModel.HomeBoardViewModel
import com.example.withpet.viewModel.HomeBoardViewModelFactory
import com.example.withpet.viewModel.bookmark.BookmarkListViewModel
import com.example.withpet.viewModel.bookmark.BookmarkListViewModelFactory
import com.example.withpet.viewModel.home.HomeBoardDetailViewModel
import com.example.withpet.viewModel.home.HomeBoardDetailViewModelFactory

class BookmarkFragment : Fragment() {

    private lateinit var binding : FragmentBookmarkBinding
    private lateinit var adapter : BookmarkRVAdapter
    private lateinit var viewModel : BookmarkListViewModel
    private lateinit var boardViewModel : HomeBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)

        val bookmarkRepository = BookmarkRepository(requireActivity().application)
        viewModel = ViewModelProvider(this, BookmarkListViewModelFactory(bookmarkRepository)).get(
            BookmarkListViewModel::class.java
        )

        val boardRepository = HomeBoardRepository(requireActivity().application)
        boardViewModel = ViewModelProvider(this, HomeBoardViewModelFactory(boardRepository)).get(
            HomeBoardViewModel::class.java
        )

        adapter = BookmarkRVAdapter()
            .apply { onClick = this@BookmarkFragment::startHomeBoardActivity}
        binding.bookmarkRv.adapter = adapter

        viewModel.getBookmarkList(FBAuth.getUid())

        viewModel.bookmarkList.observe(viewLifecycleOwner) { bookmarkList ->
            adapter.updatesList(bookmarkList)
            adapter.notifyDataSetChanged()
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_areaFragment_to_homeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_areaFragment_to_talkFragment)
        }

        binding.lifeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_areaFragment_to_lifeFragment)
        }

        binding.myTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_areaFragment_to_myFragment)
        }
        return binding.root
    }
    private fun startHomeBoardActivity(board: HomeBoard) {
        startActivity(
            Intent(context, HomeBoardActivity::class.java)
                .apply { putExtra(HomeBoardActivity.DETAIL_BOARD_ID, board.id) }
        )
    }

}