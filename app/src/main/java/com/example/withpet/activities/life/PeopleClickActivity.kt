package com.example.withpet.activities.life

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.ActivityPeopleClickBinding

class PeopleClickActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPeopleClickBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_people_click)
        var gender = "누구나"
        var age = "누구나"
        var startAge = ""
        var endAge = ""

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        binding.genderGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when(p1) {
                    R.id.allBtn -> {
                        gender = "누구나"
                    }
                    R.id.womanBtn -> {
                        gender = "여성만"
                    }
                    R.id.manBtn -> {
                        gender = "남성만"
                    }
                }
            }
        })

        binding.ageGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when(p1) {
                    R.id.ageAllBtn -> {
                        binding.selfAgeArea.visibility = View.INVISIBLE
                        age = "누구나"
                        binding.startAge.setText("")
                        binding.endAge.setText("")
                    }
                    R.id.selfBtn -> {
                        binding.selfAgeArea.visibility = View.VISIBLE
                        age=""
                        startAge = binding.startAge.text.toString()
                        endAge = binding.endAge.text.toString()
                    }

                }
            }

        })

        binding.checkBtn.setOnClickListener {
            intent.putExtra("gender", gender)
            intent.putExtra("age", age)
            intent.putExtra("startAge", binding.startAge.text.toString())
            intent.putExtra("endAge", binding.endAge.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}