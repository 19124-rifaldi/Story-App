package com.example.storyapp.view.detail

import androidx.lifecycle.*
import com.example.storyapp.api.Story
import com.example.storyapp.api.UserIdResponse
import com.example.storyapp.repo.DetailRepository
import com.example.storyapp.repo.TokenRepository
import com.example.storyapp.tool.StateData
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailRepository: DetailRepository,
    private val tokenRepository: TokenRepository
):ViewModel() {
    private val _idUser = MutableLiveData<UserIdResponse>()
    val idUser : LiveData<UserIdResponse> = _idUser


    fun getStoryByID(token:String,id:String){
        viewModelScope.launch{
            try {
                _idUser.value = detailRepository.getStoryByID(token, id)

            }catch (e:Exception){
                _idUser.value = UserIdResponse(
                    error = true,
                    message = e.message.toString(),
                    story = Story(
                        "","","","",0.0,"",0.0
                    )
                )
            }

        }
    }

    fun getToken(): LiveData<String>{
        return tokenRepository.getToken().asLiveData()
    }

}