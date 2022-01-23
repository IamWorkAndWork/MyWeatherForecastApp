package com.example.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(citiesEntityList: List<CityEntity>)

    @Query("DELETE FROM city_table")
    suspend fun deleteAllCities()

    @Query("SELECT * FROM city_table where city like :cityName")
    fun getCityByName(cityName: String): PagingSource<Int, CityEntity>

    @Query("SELECT COUNT(id) from city_table")
    suspend fun countCities(): Int

}