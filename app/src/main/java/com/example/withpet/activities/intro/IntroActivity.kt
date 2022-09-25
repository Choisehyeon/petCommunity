package com.example.withpet.activities.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.MainActivity
import com.example.withpet.R
import com.example.withpet.activities.CheckAreaActivity
import com.example.withpet.activities.member.LoginActivity
import com.example.withpet.repository.UserRepository
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IntroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val userRepository = UserRepository(this.application)
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        ).get(UserViewModel::class.java)

        auth = Firebase.auth

        if (auth.currentUser?.uid == null) {
            Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 3000)

        } else {
            viewModel.getTownById(auth.currentUser?.uid!!)
            viewModel.getRegionById(auth.currentUser?.uid!!)

            viewModel._mutableTown.observe(this) {
                if (it == "") {
                    Handler().postDelayed({
                        startActivity(Intent(this, CheckAreaActivity::class.java))
                        finish()
                    }, 3000)
                } else {
                    Handler().postDelayed({
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }, 3000)
                }
            }


        }


    }
}