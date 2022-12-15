package com.example.storyapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.api.RegisterRequest
import com.example.storyapp.databinding.ActivityRegistBinding
import com.example.storyapp.tool.showLoading
import com.example.storyapp.view.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistBinding
    private val viewModel by viewModel<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.title = getString(R.string.register_title)
        register()
        loading()
    }
    private fun register(){
        binding.apply {
            registBt.setOnClickListener {
                val name = nameEt.text.toString()
                val email = emailEt.text.toString()
                val password = passwordEt.text.toString()
                val repPass = repeatPassEt.text.toString()
                val data = RegisterRequest(
                    name = name,
                    email = email,
                    password = password
                )

                if(name.isEmpty()||email.isEmpty()
                    ||password.isEmpty()||repPass.isEmpty()){
                    Toast.makeText(this@RegisterActivity,getString(R.string.if_fill_empty),Toast.LENGTH_SHORT)
                        .show()
                }else if (password!=repPass){
                    Toast.makeText(this@RegisterActivity,getString(R.string.pass_not_same),Toast.LENGTH_SHORT)
                        .show()
                }else{
                   viewModel.register(data)
                }
            }
            check()
        }
    }

    private fun check(){
        viewModel.regResp.observe(this@RegisterActivity){
            if(!it.error){
                Toast.makeText(this@RegisterActivity,getString(R.string.acc_created),Toast.LENGTH_SHORT)
                    .show()
                val intentToLogin = Intent(this,LoginActivity::class.java)
                startActivity(intentToLogin)
                finish()
            }else{
                Toast.makeText(this@RegisterActivity, it.message,Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loading(){
        showLoading(false,binding.progressBar)
        val progressBar =binding.progressBar
        val layout = binding.registerLayout
        viewModel.loading.observe(this){
            if (it){
                showLoading(true,progressBar)
                layout.visibility = View.INVISIBLE
            }else{
                showLoading(false,progressBar)
                layout.visibility = View.VISIBLE
            }
        }
    }
}