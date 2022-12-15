package com.example.storyapp.repo

import com.example.storyapp.api.ApiService
import com.example.storyapp.api.FileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UploadStoryRepo {
    suspend fun uploadStory(file: MultipartBody.Part,desc:RequestBody,token:String):FileUploadResponse
}

class UploadStoryRepoImpl(
    private val apiService: ApiService
):UploadStoryRepo{
    override suspend fun uploadStory(
        file: MultipartBody.Part,
        desc: RequestBody,
        token: String
    ): FileUploadResponse {
        return apiService.uploadStory(token,file,desc)
    }

}