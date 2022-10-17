package com.example.withpet.activities.life

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.withpet.R
import com.example.withpet.adapter.ImageRVAdapter
import com.example.withpet.databinding.ActivityInfoWriteBinding
import com.example.withpet.entity.InfoBoard
import com.example.withpet.entity.InfoImage
import com.example.withpet.repository.InfoBoardRepository
import com.example.withpet.repository.UserRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory
import com.example.withpet.viewModel.life.InfoBoardListViewModel
import com.example.withpet.viewModel.life.InfoBoardListViewModelFactory
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class InfoWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInfoWriteBinding
    var imgList = ArrayList<Uri>()
    var infoImgList = ArrayList<String>()
    private lateinit var adapter : ImageRVAdapter
    private lateinit var userViewModel : UserViewModel
    private lateinit var infoViewModel : InfoBoardListViewModel
    private lateinit var progress_Dialog: ProgressDialog
    var place : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_write)


        val userRepository = UserRepository(this.application)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(
            UserViewModel::class.java
        )

        val infoRepository = InfoBoardRepository(this.application)
        infoViewModel = ViewModelProvider(this, InfoBoardListViewModelFactory(infoRepository)).get(
            InfoBoardListViewModel::class.java
        )

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        binding.imgBtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, 200)
        }

        binding.placeBtn.setOnClickListener {
            binding.inputPlaceArea.visibility = View.VISIBLE
        }

        binding.enterBtn.setOnClickListener {
            place = binding.infoWritePlace.text.toString()
            binding.infoWritePlace.setText("")
            binding.inputPlaceArea.visibility = View.GONE
            binding.infoPlaceArea.visibility = View.VISIBLE
            binding.infoPlace.text = place
            binding.currentPlaceNum.text = "1"
        }
        binding.exitWritePlace.setOnClickListener {
            binding.infoWritePlace.setText("")
            binding.inputPlaceArea.visibility = View.GONE
        }

        binding.exitPlace.setOnClickListener {
            place = ""
            binding.currentPlaceNum.text = "0"
            binding.infoPlaceArea.visibility = View.GONE
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ImageRVAdapter(imgList)
            .apply { onClick = this@InfoWriteActivity::removeImg }
        binding.imgRV.layoutManager = layoutManager
        binding.imgRV.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == 200) {

            if(data?.clipData != null) {
                val count = data.clipData!!.itemCount
                if(count > 10) {
                    Toast.makeText(applicationContext, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return
                }
                if(imgList.size + count > 10) {
                    Toast.makeText(applicationContext, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return
                }
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    imgList.add(imageUri)
                }

            } else { // 단일 선택
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null) {
                        imgList.add(imageUri)
                    }
                }
            }
            binding.currentImgNum.text = imgList.size.toString()
            adapter.notifyDataSetChanged()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.board_toolbar_item, menu)
        val storeBtn = menu!!.findItem(R.id.writeStoreBtn)
        storeBtn.setIcon(R.drawable.enter_gray)
        storeBtn.isEnabled = false

        binding.infoContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                storeBtn.setIcon(R.drawable.enter_gray)
                storeBtn.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!binding.infoContent.text.toString().equals("")) {
                    storeBtn.setIcon(R.drawable.enter)
                    storeBtn.isEnabled = true
                } else {
                    storeBtn.setIcon(R.drawable.enter_gray)
                    storeBtn.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.writeStoreBtn -> {

                imageUpload()


                val infoBoard = InfoBoard(0, userViewModel.getRegion(), userViewModel.getTown(),
                    binding.infoContent.text.toString(), place, FBAuth.getUid(), FBAuth.getTime(),
                    userViewModel.getNickname(), infoImgList)

                infoViewModel.insert(infoBoard)
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

    private fun removeImg(list : ArrayList<Uri>) {
        imgList = list
        binding.currentImgNum.text = imgList.size.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun imageUpload() {

        for(i in 0 until imgList.size) {
            val imageUri = imgList[i]
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            val bytes = stream.toByteArray()


            infoImgList.add(Base64.getEncoder().encodeToString(bytes))
        }
    }


    fun setdialog() {
        var message = "저장중입니다. \n잠시만 기다려주세요."
        progress_Dialog = ProgressDialog(this)
        progress_Dialog.setMessage(message)
        progress_Dialog.setCancelable(false)
        progress_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}