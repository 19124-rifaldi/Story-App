package com.example.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.tool.showLoading
import com.example.storyapp.view.createStory.CreateStoryActivity
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.maps.MapsActivity
import com.example.storyapp.view.setting.SettingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var rvMain : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        loading()
        val progressBar =binding.progressBar
        showLoading(false,progressBar)
        val layout = binding.mainLayout
        layout.visibility = View.VISIBLE


        setupAdapter()
        getStory()

        setContentView(binding.root)
        moveToCreateStory()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_setting->{
                val intentSetting = Intent(this@MainActivity,SettingActivity::class.java)
                startActivity(intentSetting)
            }R.id.logout_option->{
            val builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.want_logout))
                .setPositiveButton(
                    R.string.yes
                ) { _, _->
                    viewModel.removeToken()
                    val intentToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intentToLogin)
                    finish()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
            builder.show()
            }R.id.maps_option->{
                val intentToMaps = Intent(this,MapsActivity::class.java)
                startActivity(intentToMaps)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getStory(){
        val adapter = UserStoryAdapter()
//        binding.mainRv.adapter = adapter
        binding.mainRv.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getToken().observe(this){
            viewModel.getAllStories(bearer+it).observe(this){ data->
                adapter.submitData(lifecycle,data)
            }
        }
    }

    private fun setupAdapter(){
       binding.mainRv.layoutManager= LinearLayoutManager(this)
    }

    private fun showRvList(){

        val userStoryAdapter = UserStoryAdapter()

        rvMain.adapter = userStoryAdapter
    }

    private fun moveToCreateStory(){
        binding.floatingActionButton.setOnClickListener {
            val intentToCreateStory = Intent(this@MainActivity,CreateStoryActivity::class.java)
            startActivity(intentToCreateStory)
        }
    }
    private fun check(){
        viewModel.story.observe(this){
            showRvList()

        }
    }


    override fun onResume() {
        super.onResume()
        check()
    }

    companion object{
        private const val bearer = "Bearer "
    }

}