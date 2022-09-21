package com.example.withpet.activities.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.R
import com.example.withpet.databinding.ActivityLoginBinding
import com.example.withpet.repository.UserRepository
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val userRepository = UserRepository(this.application)
        viewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)

    }

    fun loginClick(view : View) {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        viewModel.login(this, email, password)
    }
    fun onClick(view : View) {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }
}