package com.example.urbandictionaryapp.di

import com.example.urbandictionaryapp.data.db.AppRoomDb
import com.example.urbandictionaryapp.data.repo.DefinitionRepo
import com.example.urbandictionaryapp.data.services.RapidAPIServices
import com.example.urbandictionaryapp.viewModel.CustomViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppRoomDb.buildDatabase(androidContext()) }
    single { RapidAPIServices()}
    single { DefinitionRepo(get(), get()) }

    viewModel { CustomViewModel(get()) }
}