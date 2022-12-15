package com.example.storyapp.tool

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context){

    suspend fun saveToken(saveToken:String){
        context.userToken.edit {
            it[KEY] = saveToken
        }
    }

    fun getToken() : Flow<String> {
        return context.userToken.data.map {
            it[KEY] ?: ""
        }
    }

    suspend fun removeToken(){
        context.userToken.edit {
            it[KEY] = ""
        }
    }
    companion object{
        private const val token = "user_token"
        private val KEY = stringPreferencesKey("key")
        private val Context.userToken by preferencesDataStore(token)
    }
}