package com.example.storyapp.view.createStory

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityCreateStoryBinding
import com.example.storyapp.tool.createCustomTempFile
import com.example.storyapp.tool.reduceFileImage
import com.example.storyapp.tool.uriToFile
import com.example.storyapp.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CreateStoryActivity : AppCompatActivity() {
    private var getFile: File? = null
    private lateinit var binding : ActivityCreateStoryBinding
    private val viewModel by viewModel<CreateStoryViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.title = getString(R.string.create_title)
        getPhoto()

        binding.uploadStoryBt.setOnClickListener {
            uploadImage()
            val intentToMain = Intent(this@CreateStoryActivity,MainActivity::class.java)
            startActivity(intentToMain)
            finish()
        }

    }
    private fun getPhoto(){
        binding.apply {
            getPhotoBt.setOnClickListener {
                val dialog = BottomSheetDialog(this@CreateStoryActivity)
                val view = layoutInflater.inflate(R.layout.media_item,null)
                val camera = view.findViewById<ImageView>(R.id.camera_bt)
                val gallery = view.findViewById<ImageView>(R.id.gallery_bt)
                camera.setOnClickListener {
                    permission()

                    dialog.dismiss()
                }
                gallery.setOnClickListener {
                    startGallery()
                    dialog.dismiss()
                }

                dialog.setContentView(view)
                dialog.show()
            }
        }
    }
    private fun permission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
            startTakePhoto()
        }else{
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),102)
        }
    }
    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode== RESULT_OK){
            val myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile.path)
            getFile = myFile
            binding.photoIv.setImageBitmap(result)
        }
    }
    private fun startTakePhoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI : Uri = FileProvider.getUriForFile(
                this@CreateStoryActivity,
                "com.com.example.storyapp.MyApp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
            launcherIntentCamera.launch(intent)
        }
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {

            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@CreateStoryActivity)
            getFile = myFile
            binding.photoIv.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        if (getFile != null) {
            val fileImg = reduceFileImage(getFile as File)
            val desc = binding.edAddDescription.text.toString()
            val description = desc.toRequestBody("text/plain".toMediaType())
            val requestImageFile = fileImg.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                fileImg.name,
                requestImageFile
            )
            viewModel.getToken().observe(this){token->
                viewModel.uploadStory(imageMultipart,description,bearer + token)
            }


            viewModel.uploadResp.observe(this){
                if (!it.error){
                    Toast.makeText(this@CreateStoryActivity, it.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@CreateStoryActivity, it.message, Toast.LENGTH_SHORT).show()
                }

            }

        } else {
            Toast.makeText(this@CreateStoryActivity, getString(R.string.input_picture_first), Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        private const val bearer = "Bearer "
    }


}