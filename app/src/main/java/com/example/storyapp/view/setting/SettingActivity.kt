package com.example.storyapp.view.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.databinding.ActivitySettingBinding


class SettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.title = "Setting"
        changeLang()
    }

    private fun changeLang(){
        binding.changeLangBt.setOnClickListener {
            val setting = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(setting)
        }
    }


}