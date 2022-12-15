package com.example.storyapp.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.api.LoginRequest
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.tool.showLoading
import com.example.storyapp.view.main.MainActivity
import com.example.storyapp.view.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val viewModel by viewModel<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.title = getString(R.string.login_tittle)

        moveToRegister()
        checkLogin()
        getUser()
        getLoginData()
        loading()

    }
    private fun getUser(){

        binding.button.setOnClickListener {
            val data = LoginRequest(
                email = binding.emailEt.text.toString(),
                password = binding.passwordEt.text.toString()
            )

            if(binding.emailEt.text!!.isEmpty()||binding.passwordEt.text!!.isEmpty()){
                Toast.makeText(this@LoginActivity, getString(R.string.if_fill_empty), Toast.LENGTH_SHORT).show()
            }else{
                viewModel.loginToHome(data)
            }

        }

    }
    private fun getLoginData(){

        binding.apply {
            viewModel.login.observe(this@LoginActivity) {
                if(!it.error){
                    moveToMain()
                    viewModel.saveToken(it.loginResult.token)
                    Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
//
        }
    }

    private fun checkLogin(){
        viewModel.getToken().observe(this){
            when{
                it!="" ->{
                    moveToMain()
                }
            }
        }
    }

    private fun moveToMain(){
        finish()
        val intent =Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
    }
    private fun moveToRegister(){
        binding.registerBt.setOnClickListener {
            val intentToRegister = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intentToRegister)
        }

    }
    private fun loading(){
        showLoading(false,binding.progressBar)
        val progressBar =binding.progressBar
        val layout = binding.loginLayout
        viewModel.loading.observe(this){
            if (it){
                closeKeyboard()
                showLoading(true,progressBar)
                layout.visibility = View.INVISIBLE
            }else{
                showLoading(false,progressBar)
                layout.visibility = View.VISIBLE
            }
        }
    }

    private fun closeKeyboard(){
        val view:View? = currentFocus
        val inputMethodManager:InputMethodManager
        when{
            view!= null ->{
                inputMethodManager= getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
            }
        }
    }

}