package com.example.withpet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.withpet.R
import com.example.withpet.databinding.FragmentTalkBinding

class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_talk, container, false )

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.lifeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_lifeFragment)
        }

        binding.areaTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_areaFragment)
        }

        binding.myTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_myFragment)
        }

        return binding.root
    }

}