package com.example.urbandictionaryapp.common

import com.example.urbandictionaryapp.data.model.Definition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

val listType: Type = object : TypeToken<ArrayList<Definition>>() {}.type
val gson = Gson()

fun listToJson(list: List<Definition>): String = gson.toJson(list, listType)
fun listFromJson(string: String): List<Definition> = gson.fromJson(string, listType)