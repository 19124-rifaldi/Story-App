package com.example.storyapp

import android.app.Application
import com.example.storyapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule,
                dataStoreModule,
                databaseModule
            )
        }
    }
}