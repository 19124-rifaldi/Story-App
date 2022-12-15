package com.example.storyapp.repo

import com.example.storyapp.api.*

interface AuthRepo {
    suspend fun login(loginRequest: LoginRequest):LoginResponse
    suspend fun register(registerRequest: RegisterRequest):RegistResponse
}

class AuthRepoImpl(
    private val authApiService: ApiService
):AuthRepo{
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return authApiService.login(loginRequest)
    }

    override suspend fun register(registerRequest: RegisterRequest): RegistResponse {
        return authApiService.register(registerRequest)
    }

}