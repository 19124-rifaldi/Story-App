package com.example.storyapp.repo

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.storyapp.api.ApiService
import com.example.storyapp.api.GetAllStoriesResponse
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.room.StoryDatabase
import com.example.storyapp.room.StoryRemoteMediator

interface MainRepository {
    fun getAllStories(token:String): LiveData<PagingData<ListStoryItem>>
    suspend fun getStoriesAndLocation(token:String,location:Int):GetAllStoriesResponse
}

class MainRepositoryImpl(
    private val authApiService: ApiService,
    private val storyDatabase: StoryDatabase
):MainRepository{
    override fun getAllStories(token: String): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, authApiService,token),
            pagingSourceFactory = {

                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    override suspend fun getStoriesAndLocation(
        token: String,
        location: Int
    ): GetAllStoriesResponse {
        return authApiService.getStoriesAndLocation(token,location)
    }

}