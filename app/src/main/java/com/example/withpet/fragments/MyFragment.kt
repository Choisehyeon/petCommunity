package com.example.withpet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.withpet.R
import com.example.withpet.databinding.FragmentMyBinding

class MyFragment : Fragment() {

    private lateinit var binding : FragmentMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false )


        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_myFragment_to_homeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_myFragment_to_talkFragment)
        }

        binding.lifeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_myFragment_to_lifeFragment)
        }

        binding.areaTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_myFragment_to_areaFragment)
        }

        return binding.root
    }

}