package com.example.storyapp.repo

import com.example.storyapp.api.ApiService
import com.example.storyapp.api.UserIdResponse

interface DetailRepository {
    suspend fun getStoryByID(token:String, id:String): UserIdResponse
}
class DetailRepositoryImpl(
    private val authApiService: ApiService,

):DetailRepository{
    override suspend fun getStoryByID(token: String, id: String): UserIdResponse  {
        return authApiService.getStoriesByID(token,id)
    }

}