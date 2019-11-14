package com.example.urbandictionaryapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UrbanDictionaryApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UrbanDictionaryApp)
            modules(appModule)
        }
    }
}