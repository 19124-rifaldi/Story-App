package com.example.storyapp.di

import com.example.storyapp.view.createStory.CreateStoryViewModel
import com.example.storyapp.view.detail.DetailViewModel
import com.example.storyapp.view.login.LoginViewModel
import com.example.storyapp.view.main.MainViewModel
import com.example.storyapp.view.maps.MapsViewModel
import com.example.storyapp.view.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get(),get()) }
    viewModel { MainViewModel(get(),get()) }
    viewModel { DetailViewModel(get(),get()) }
    viewModel { CreateStoryViewModel(get(),get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { MapsViewModel(get(),get()) }

}