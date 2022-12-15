package com.example.storyapp.view.login

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.api.LoginRequest
import com.example.storyapp.api.LoginResponse
import com.example.storyapp.api.LoginResult
import com.example.storyapp.repo.AuthRepo
import com.example.storyapp.repo.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthRepo,
    private val tokenRepository: TokenRepository
):ViewModel() {
    private val _login = MutableLiveData<LoginResponse>()
    val login : LiveData<LoginResponse> = _login

    private val _state = MutableLiveData<Boolean>()
    val state : LiveData<Boolean> = _state

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading



    fun loginToHome(loginRequest: LoginRequest){
        viewModelScope.launch {
            _loading.value = true

            try {
                val data = authRepo.login(loginRequest)
                _login.value = data
                _state.value = true
                _loading.value = false

            }catch (e:Exception){
                Log.e("TAG", "onFailure: ${e.message.toString()}")
                _login.value = LoginResponse(
                    LoginResult("","",""),
                    error = true,
                    message = e.message.toString()
                )
                _state.value =false
                _loading.value = false
            }

        }
    }

    fun saveToken(token:String){
        viewModelScope.launch {
            tokenRepository.saveToken(token)
        }
    }

    fun getToken():LiveData<String>{
        return tokenRepository.getToken().asLiveData()
    }


}