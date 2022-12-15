package com.example.storyapp.view.createStory


import androidx.lifecycle.*
import com.example.storyapp.api.FileUploadResponse
import com.example.storyapp.repo.TokenRepository
import com.example.storyapp.repo.UploadStoryRepo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoryViewModel(
    private val uploadStoryRepo: UploadStoryRepo,
    private val tokenRepository: TokenRepository
):ViewModel() {
    private val _uploadResp = MutableLiveData<FileUploadResponse>()
    val uploadResp : LiveData<FileUploadResponse> = _uploadResp


    fun getToken(): LiveData<String>{
        return tokenRepository.getToken().asLiveData()
    }

    fun uploadStory(file: MultipartBody.Part, desc: RequestBody, token:String){
        viewModelScope.launch {
            try {
                val response = uploadStoryRepo.uploadStory(file,desc,token)
                _uploadResp.value = response
            }catch (e:Exception){
                _uploadResp.value = FileUploadResponse(
                    error = true,
                    message = e.message.toString()
                )
            }
        }
    }


}