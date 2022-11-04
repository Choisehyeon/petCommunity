package com.example.withpet.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.withpet.R
import com.example.withpet.activities.home.BoardWriteActivity
import com.example.withpet.activities.home.HomeBoardActivity
import com.example.withpet.adapter.HomeBoardRVAdapter
import com.example.withpet.databinding.FragmentHomeBinding
import com.example.withpet.entity.HomeBoard
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.repository.UserRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.utils.toVisibility
import com.example.withpet.viewModel.HomeBoardViewModel
import com.example.withpet.viewModel.HomeBoardViewModelFactory
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var boardViewModel: HomeBoardViewModel
    private lateinit var adapter: HomeBoardRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val userRepository = UserRepository(requireActivity().application)
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        ).get(UserViewModel::class.java)

        val homeBoardRepository = HomeBoardRepository(requireActivity().application)
        boardViewModel =
            ViewModelProvider(this, HomeBoardViewModelFactory(homeBoardRepository)).get(
                HomeBoardViewModel::class.java
            )

        adapter = HomeBoardRVAdapter()
            .apply { onClick = this@HomeFragment::startHomeBoardActivity }
        binding.boardRv.adapter = adapter

        getBoardData()

        boardViewModel.progressVisible.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = it.toVisibility()
        }

        binding.lifeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_lifeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_areaFragment)
        }

        binding.myTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_myFragment)
        }

        binding.writeBtn.setOnClickListener {

            startActivity(
                Intent(context, BoardWriteActivity::class.java)
                    .apply {
                        putExtra(BoardWriteActivity.TOWN_NAME, viewModel._mutableTown.value!!)
                        putExtra(BoardWriteActivity.REGION_NAME, viewModel._mutableRegion.value!!)
                    })

        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            getBoardData()
        }
    }

    private fun getBoardData() {
        viewModel.getUser(FBAuth.getUid())
        viewModel._mutableUser.observe(viewLifecycleOwner) {
            binding.townName.text = it.town
            boardViewModel.list(it.region, it.town)
        }

        boardViewModel.homeboardList.observe(viewLifecycleOwner) {
            adapter.updatesList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun startHomeBoardActivity(board: HomeBoard) {
        startActivity(
            Intent(context, HomeBoardActivity::class.java)
                .apply { putExtra(HomeBoardActivity.DETAIL_BOARD_ID, board.id) }
        )
    }

}