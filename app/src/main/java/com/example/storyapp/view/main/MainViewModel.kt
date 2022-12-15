package com.example.storyapp.view.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.repo.MainRepository
import com.example.storyapp.repo.TokenRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val tokenRepository: TokenRepository,
    private val mainRepository: MainRepository
):ViewModel() {
    private var _story = MutableLiveData<List<ListStoryItem>>()
    val story : LiveData<List<ListStoryItem>> = _story


    fun getAllStories(token:String): LiveData<PagingData<ListStoryItem>> =
        mainRepository.getAllStories(token).cachedIn(viewModelScope)

    fun getToken(): LiveData<String>{
        return tokenRepository.getToken().asLiveData()
    }
    fun removeToken(){
        viewModelScope.launch {
            tokenRepository.removeToken()
        }
    }

}