package com.example.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModel<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentData = intent.getParcelableExtra<ListStoryItem>("id")
        val getID = intentData?.id.toString()
        getToken(getID)


    }

    private fun getToken(id:String){
        viewModel.getToken().observe(this){
            viewModel.getStoryByID(bearer+it, id)
            Log.d("id nya ada ga",id)
            Log.d("token nya ada ga",bearer+it)
        }
        getDetail()
    }

    private fun getDetail(){
        viewModel.idUser.observe(this@DetailActivity){
            when(it.error){
                true->{
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
                false->{
                    setup()
                }
            }
        }
    }
    companion object{
        private const val bearer = "Bearer "
    }
    private fun setup() {
        viewModel.idUser.observe(this) {
            if (it.story.id.isNotEmpty()){
                binding.apply {
                    this@DetailActivity.title = "${it.story.name} Story"
                    tvDetailName.text = it.story.name
                    Glide.with(this@DetailActivity)
                        .load(it.story.photoUrl)
                        .into(ivDetailPhoto)

                    editTextTextMultiLine2.setText(it.story.description)

                }
            }

        }
    }


}