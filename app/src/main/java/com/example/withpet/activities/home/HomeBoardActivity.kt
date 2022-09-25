package com.example.withpet.activities.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.withpet.R

class HomeBoardActivity : AppCompatActivity() {

    companion object {
        const val TOWN_NAME = "town"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_board)
    }
}