package com.example.storyapp.repo

import com.example.storyapp.tool.UserPreferences
import kotlinx.coroutines.flow.Flow


interface TokenRepository{
    fun getToken(): Flow<String>
    suspend fun saveToken(saveToken:String)
    suspend fun removeToken()
}
class TokenRepositoryImpl(
    private val userPreferences: UserPreferences
):TokenRepository {
    override fun getToken(): Flow<String> {
        return userPreferences.getToken()
    }

    override suspend fun saveToken(saveToken: String) {
        userPreferences.saveToken(saveToken)
    }

    override suspend fun removeToken() {
        userPreferences.removeToken()
    }
}