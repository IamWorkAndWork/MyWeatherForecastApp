package com.example.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.Constants
import com.example.data.local.dao.CityDao
import com.example.domain.model.CityEntity

@Database(
    version = 1,
    entities = [CityEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constants.DATABASE_NAME
            ).build()
        }
    }

}