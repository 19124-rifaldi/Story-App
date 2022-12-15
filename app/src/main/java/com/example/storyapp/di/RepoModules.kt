package com.example.storyapp.di

import com.example.storyapp.repo.*
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepo> { AuthRepoImpl(get()) }
    single<TokenRepository> { TokenRepositoryImpl(get()) }
    single<MainRepository> { MainRepositoryImpl(get(),get()) }
    single<DetailRepository> { DetailRepositoryImpl(get()) }
    single<UploadStoryRepo> { UploadStoryRepoImpl(get()) }
}