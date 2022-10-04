package com.example.withpet.activities.life

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.R
import com.example.withpet.databinding.ActivityWithBoardBinding
import com.example.withpet.entity.HomeBoard
import com.example.withpet.entity.WithBoard
import com.example.withpet.repository.UserRepository
import com.example.withpet.repository.WithBoardRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.example.withpet.viewModel.home.HomeBoardDetailViewModel
import com.example.withpet.viewModel.home.HomeBoardDetailViewModelFactory
import com.example.withpet.viewModel.life.WithBoardListViewModel
import com.example.withpet.viewModel.life.WithBoardListViewModelFactory
import java.util.*

class WithBoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWithBoardBinding
    private lateinit var viewModel: WithBoardListViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var progress_Dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_with_board)

        val repository = WithBoardRepository(this.application)
        viewModel = ViewModelProvider(this, WithBoardListViewModelFactory(repository)).get(
            WithBoardListViewModel::class.java
        )

        val userRepository = UserRepository(this.application)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(
            UserViewModel::class.java
        )

        val cal = Calendar.getInstance()
        var date = ""
        var time = ""

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit_bold)

        binding.plusBtn.setOnClickListener {
            viewModel.plusPeople()
        }

        binding.minusBtn.setOnClickListener {
            viewModel.minusPeople()
        }

        viewModel.limitPeople.observe(this) {
            if (it <= 3) {
                binding.minusBtn.visibility = View.INVISIBLE
            } else if (it >= 10) {
                binding.plusBtn.visibility = View.INVISIBLE
            } else {
                binding.plusBtn.visibility = View.VISIBLE
                binding.minusBtn.visibility = View.VISIBLE
            }
            binding.limitPeople.text = "${it}명"
        }

        binding.peopleClick.setOnClickListener {
            startActivityForResult(Intent(this, PeopleClickActivity::class.java), 200)
        }

        binding.dateClick.setOnClickListener {
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                    binding.withBoardDate.text = "$y/${m + 1}/$d"
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.timeClick.setOnClickListener {
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
                binding.withBoardTime.text = "${h}시 ${m}분"
            }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            val gender = data?.getStringExtra("gender")
            val age = data?.getStringExtra("age")
            val startAge = data?.getStringExtra("startAge")
            val endAge = data?.getStringExtra("endAge")

            if (age.equals("누구나")) {
                binding.gender.text = gender
            } else if (gender.equals("누구나") && age.equals("")) {
                binding.age.text = startAge + "세 ~ " + endAge + "세,"
            } else if (!gender.equals("누구나") && age.equals("")) {
                binding.gender.text = gender
                binding.age.text = startAge + "세 ~ " + endAge + "세,"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.board_toolbar_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.writeStoreBtn -> {
                val withBoard = WithBoard(
                    0,
                    userViewModel.getRegion(),
                    userViewModel.getTown(),
                    binding.wbWriteTitle.text.toString(),
                    binding.wbWriteContent.text.toString(),
                    viewModel.getPeople(),
                    binding.gender.text.toString(),
                    binding.age.text.toString(),
                    binding.withBoardDate.text.toString(),
                    binding.withBoardTime.text.toString(),
                    binding.wbWritePlace.text.toString(),
                    FBAuth.getTime(),
                    FBAuth.getUid(),
                    userViewModel.getNickname(),
                    null
                )
                viewModel.insert(withBoard)
                setResult(RESULT_OK, intent)
                setdialog()
                progress_Dialog.show()
                Handler().postDelayed({
                    progress_Dialog.dismiss()
                    finish()
                }, 3000)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setdialog() {
        var message = "저장중입니다. \n잠시만 기다려주세요."
        progress_Dialog = ProgressDialog(this)
        progress_Dialog.setMessage(message)
        progress_Dialog.setCancelable(false)
        progress_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }
}