package com.example.withpet.activities.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.MainActivity
import com.example.withpet.R
import com.example.withpet.databinding.ActivityLoginBinding
import com.example.withpet.repository.UserRepository
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel : UserViewModel
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = Firebase.auth

        val userRepository = UserRepository(this.application)
        viewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)

    }

    fun loginClick(view : View) {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            println("Login Success")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }.addOnFailureListener {
            println("Login Error")
        }
    }


    fun onClick(view : View) {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }
}