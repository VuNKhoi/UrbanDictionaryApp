package com.example.urbandictionaryapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDb: RoomDatabase() {

    companion object{
        private const val dbName = "Definition.db"

        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppRoomDb::class.java,
            dbName).build()
    }

    abstract fun definitionDao(): DefinitionDao
}