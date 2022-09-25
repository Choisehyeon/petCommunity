package com.example.withpet.activities.home

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.withpet.R
import com.example.withpet.databinding.ActivityBoardWriteBinding
import com.example.withpet.entity.HomeBoard
import com.example.withpet.repository.HomeBoardRepository
import com.example.withpet.repository.UserRepository
import com.example.withpet.utils.FBAuth
import com.example.withpet.viewModel.HomeBoardViewModel
import com.example.withpet.viewModel.HomeBoardViewModelFactory
import com.example.withpet.viewModel.UserViewModel
import com.example.withpet.viewModel.UserViewModelFactory

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding
    private lateinit var viewModel : HomeBoardViewModel
    private var image : Uri? = null
    private lateinit var boardImg : Bitmap
    private var isImageUpload = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        val boardRepository = HomeBoardRepository(this.application)
        viewModel = ViewModelProvider(this, HomeBoardViewModelFactory(boardRepository)).get(HomeBoardViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.exit)

        binding.plusImgBtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 200)

            isImageUpload = true
        }





    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            binding.boardImg.setImageURI(data?.data)
            image = data?.data!!
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.board_toolbar_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.writeStoreBtn -> {

                val title = binding.boardTitle.text.toString()
                val content = binding.boardContent.text.toString()
                val price = binding.boardPrice.text.toString()

                val region = intent.getStringExtra("region")
                val town = intent.getStringExtra("town")

                if (title.equals("")) {
                    Toast.makeText(this, "제목을 입력하세요.", Toast.LENGTH_LONG).show()
                }
                if (content.equals("")) {
                    Toast.makeText(this, "내용을 입력하세요.", Toast.LENGTH_LONG).show()
                }

                if (price.equals("")) {
                    Toast.makeText(this, "가격을 입력하세요.", Toast.LENGTH_LONG).show()
                }

                if (image == null) {
                    Toast.makeText(this, "이미지를 넣어주세요.", Toast.LENGTH_LONG).show()
                }

                if (!title.equals("") && !content.equals("") && !price.equals("") && image != null) {

                    imageUpload()

                    val board = HomeBoard(
                        0, region!!, town!!, title, content,
                        FBAuth.getTime(), price, FBAuth.getUid(), boardImg)

                    viewModel.insert(board)
                    finish()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun imageUpload() {
        val imageUri = image
        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri)
        boardImg = bitmap

    }
}