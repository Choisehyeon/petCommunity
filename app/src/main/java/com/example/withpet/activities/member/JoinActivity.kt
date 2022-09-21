package com.example.withpet.activities.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.R
import com.example.withpet.databinding.ActivityJoinBinding
import com.example.withpet.repository.UserRepository
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory

class JoinActivity : AppCompatActivity() {

    private lateinit var binding : ActivityJoinBinding
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        val userRepository = UserRepository(this.application)
        viewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.joinPw.text.toString().equals(binding.confirmPw.text.toString())) {
                    binding.check.text = "일치"
                } else {
                    binding.check.text = "불일치"
                }
            }

        }

        binding.confirmPw.addTextChangedListener(textWatcher)


    }
    fun joinClick(view : View) {
        val email = binding.joinEmail.text.toString()
        val password = binding.joinPw.text.toString()
        val nickname = binding.joinNickname.text.toString()

        viewModel.join(this, email, password, nickname)
    }
}