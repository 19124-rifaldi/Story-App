package com.example.storyapp.view.maps

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.repo.MainRepository
import com.example.storyapp.repo.TokenRepository
import kotlinx.coroutines.launch

class MapsViewModel(
    private val tokenRepository: TokenRepository,
    private val mainRepository: MainRepository
):ViewModel() {
    private var _story = MutableLiveData<List<ListStoryItem>>()
    val story : LiveData<List<ListStoryItem>> = _story

    fun getMapsStory(token:String){
        viewModelScope.launch {
            try {
                val data =mainRepository.getStoriesAndLocation(token,location)
                _story.value = data.listStory
            }catch (e:Exception){
                Log.e("halo",e.toString())
            }

        }
    }

    fun getToken(): LiveData<String>{
        return tokenRepository.getToken().asLiveData()
    }

    companion object{
        private const val location = 1
    }
}