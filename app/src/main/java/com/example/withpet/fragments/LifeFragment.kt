package com.example.withpet.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.withpet.R
import com.example.withpet.activities.life.InfoListActivity
import com.example.withpet.activities.life.WithActivity
import com.example.withpet.databinding.FragmentLifeBinding


class LifeFragment : Fragment() {

    private lateinit var binding : FragmentLifeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_life, container, false)

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_lifeFragment_to_homeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_lifeFragment_to_talkFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_lifeFragment_to_areaFragment)
        }

        binding.myTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_lifeFragment_to_myFragment)
        }

        binding.withBtn.setOnClickListener {
            startActivity(
                Intent(context, WithActivity::class.java)
            )
        }

        binding.infoBtn.setOnClickListener {
            startActivity(
                Intent(context, InfoListActivity::class.java)
            )
        }

        return binding.root
    }

}