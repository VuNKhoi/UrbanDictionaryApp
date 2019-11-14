package com.example.urbandictionaryapp.data.services

import com.example.urbandictionaryapp.data.model.APIResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DefinitionService{

    @Headers("x-rapidapi-key: 266a24e049mshbc70d9bbe8ea6d8p147d5cjsnd0996c442f11")
    @GET("define")
    suspend fun getDefinitions(@Query(value = "term") string: String): APIResponse
}