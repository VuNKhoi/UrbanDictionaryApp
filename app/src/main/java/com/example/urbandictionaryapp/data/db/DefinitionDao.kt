package com.example.urbandictionaryapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DefinitionDao {
    @Query("SELECT * FROM DbEntity WHERE word LIKE :searchWord")
    fun findDefinitionsByWord(searchWord: String): List<DbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDefinitions(vararg dbEntity: DbEntity)
}