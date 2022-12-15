package com.example.storyapp.di

import androidx.room.Room
import com.example.storyapp.room.StoryDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<StoryDatabase>().storyDao() }
    single{
        Room.databaseBuilder(
            androidContext(),
            StoryDatabase::class.java,
            "database_story1"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}