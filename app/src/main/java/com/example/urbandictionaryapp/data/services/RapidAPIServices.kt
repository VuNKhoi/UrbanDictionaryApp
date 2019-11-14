package com.example.urbandictionaryapp.data.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RapidAPIServices {
    private val baseUrl = "https://mashape-community-urban-dictionary.p.rapidapi.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val definitionService: DefinitionService = retrofit.create(DefinitionService::class.java)
}