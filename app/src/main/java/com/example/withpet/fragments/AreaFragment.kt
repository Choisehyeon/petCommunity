package com.example.withpet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.withpet.R
import com.example.withpet.databinding.FragmentAreaBinding

class AreaFragment : Fragment() {

    private lateinit var binding : FragmentAreaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_area, container, false)

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

}