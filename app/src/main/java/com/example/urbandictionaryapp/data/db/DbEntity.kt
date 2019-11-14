package com.example.urbandictionaryapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbEntity(
    @PrimaryKey val word: String,
    @ColumnInfo(name = "json") val json: String)