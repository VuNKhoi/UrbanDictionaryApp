package com.example.urbandictionaryapp.data.repo

import android.util.Log
import com.example.urbandictionaryapp.common.listFromJson
import com.example.urbandictionaryapp.common.listToJson
import com.example.urbandictionaryapp.data.db.AppRoomDb
import com.example.urbandictionaryapp.data.db.DbEntity
import com.example.urbandictionaryapp.data.model.Definition
import com.example.urbandictionaryapp.data.services.RapidAPIServices
import com.google.gson.Gson
import java.lang.Exception
import java.util.*
import com.google.gson.reflect.TypeToken


class DefinitionRepo(
    private val _appDb: AppRoomDb,
    private val _appService: RapidAPIServices
) {

    suspend fun getDefinitions(string: String): List<Definition>? {
        return try {
            val definitions = fetchDefinitions(string)
            putDefinitionsInDb(definitions)
            definitions
        } catch (e: Exception) {
            Log.e("FETCH DEF ERROR", e.message ?: "")
            getDefinitionFromDb(string)
        }
    }

    private suspend fun fetchDefinitions(string: String) = _appService
        .definitionService
        .getDefinitions(string)
        .list

    private fun putDefinitionsInDb(list: List<Definition>) {
        val word = list[0].word.toLowerCase(Locale.getDefault())
        val json = listToJson(list)
        _appDb
            .definitionDao()
            .insertDefinitions(DbEntity(word, json))
    }

    private fun getDefinitionFromDb(string: String): List<Definition>? {
        val dbResult = _appDb
            .definitionDao()
            .findDefinitionsByWord(string.toLowerCase(Locale.getDefault()))
        return if (dbResult.isNotEmpty()) {
            dbResult[0].run {
                listFromJson(this.json)
            }
        } else null
    }
}