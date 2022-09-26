package com.example.withpet.activities.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.R
import com.example.withpet.activities.CheckAreaActivity
import com.example.withpet.databinding.ActivityJoinBinding
import com.example.withpet.repository.UserRepository
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JoinActivity : AppCompatActivity() {

    private lateinit var binding : ActivityJoinBinding
    lateinit var viewModel: UserViewModel
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        auth = Firebase.auth

        val userRepository = UserRepository(this.application)
        viewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)
        viewModel.getNicknameList()


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

        viewModel.nicknameList.observe(this) {

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if(it.contains(nickname)) {
                        binding.nicknameCheck.text = "중복"
                    } else {
                        binding.nicknameCheck.text = "사용가능"
                    }
                }

            }
            binding.joinNickname.addTextChangedListener(textWatcher)
        }

        if(binding.nicknameCheck.text.toString().equals("중복")) {
            Toast.makeText(this, "닉네임을 바꿔주세요.", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    viewModel.join(this, email, password, nickname)
                    val intent = Intent(this, CheckAreaActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {

                }
        }
    }
}