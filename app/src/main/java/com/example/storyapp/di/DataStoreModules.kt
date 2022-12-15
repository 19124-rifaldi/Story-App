package com.example.storyapp.di

import com.example.storyapp.tool.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single{
        UserPreferences(androidContext())
    }
}