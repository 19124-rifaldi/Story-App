package com.example.storyapp.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.api.RegistResponse
import com.example.storyapp.api.RegisterRequest
import com.example.storyapp.repo.AuthRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepo: AuthRepo
):ViewModel() {

    private val _regResp = MutableLiveData<RegistResponse>()
    val regResp : LiveData<RegistResponse> = _regResp


    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    fun register(registerRequest: RegisterRequest){
        viewModelScope.launch {
            _loading.value = true
            delay(1000)
            try {
                val data = authRepo.register(registerRequest)
                _regResp.value = data
                _loading.value = false
            }catch (e:Exception){
                Log.e("error at regVM","fail , e")
                _regResp.value=RegistResponse(
                    error = true,
                    message = e.message.toString()
                )
                _loading.value = false
            }

        }
    }
}