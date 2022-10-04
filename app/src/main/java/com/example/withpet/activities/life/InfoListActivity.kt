package com.example.withpet.activities.life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.ActivityInfoListBinding

class InfoListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInfoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_list)


        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.withboard_toolbar_item, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.writeBtn -> {
                val intent = Intent(this, InfoWriteActivity::class.java)
                startActivityForResult(intent, 200)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}