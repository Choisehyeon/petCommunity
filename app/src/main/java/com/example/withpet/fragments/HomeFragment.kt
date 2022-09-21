package com.example.withpet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.withpet.R
import com.example.withpet.databinding.FragmentHomeBinding
import com.example.withpet.repository.UserRepository
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel: UserViewModel

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
        viewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)
        binding.user = viewModel

        binding.lifeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_lifeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }

        binding.areaTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_areaFragment)
        }

        binding.myTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_myFragment)
        }

        return binding.root
    }

}