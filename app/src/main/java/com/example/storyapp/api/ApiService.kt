package com.example.storyapp.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest : LoginRequest):LoginResponse

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest):RegistResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token:String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ):GetAllStoriesResponse

    @GET("stories")
    suspend fun getStoriesAndLocation(
        @Header("Authorization") token:String,
        @Query("location") location: Int,
    ):GetAllStoriesResponse

    @GET("stories/{id}")
    suspend fun getStoriesByID(
        @Header("Authorization") token:String,
        @Path("id")id:String
    ): UserIdResponse

    @Multipart
    @POST("/v1/stories")
    suspend fun uploadStory(
        @Header("Authorization") token:String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse

}