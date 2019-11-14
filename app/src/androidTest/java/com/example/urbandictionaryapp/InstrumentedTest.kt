package com.example.urbandictionaryapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.urbandictionaryapp.common.createDefinitionList
import com.example.urbandictionaryapp.data.db.AppRoomDb
import com.example.urbandictionaryapp.data.db.DbEntity
import com.example.urbandictionaryapp.data.db.DefinitionDao
import com.example.urbandictionaryapp.data.model.Definition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.util.ArrayList

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private lateinit var db: AppRoomDb
    private lateinit var dao: DefinitionDao
    private val gson = Gson()
    private val listType = object : TypeToken<ArrayList<Definition>>() {}.type


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppRoomDb::class.java).build()
        dao = db.definitionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun givenListDefinition_WriteListToDb_DbStoresValue() {
        val word = "word"
        val def = createDefinitionList(word)

        val json = gson.toJson(def, listType)
        dao.insertDefinitions(DbEntity(word, json))

        val dbReadResult = dao.findDefinitionsByWord(word)
        assert(dbReadResult.size == 1)

        if (dbReadResult.isNotEmpty()) {
            assertEquals(
                dbReadResult[0]
                    .run { gson.fromJson(this.json, listType) as List<Definition> }[0]
                    .word,
                word
            )
        }
    }
}
