package com.example.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.domain.model.CityEntity
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    fun getRemoteCitiesList(): Flow<List<CityEntity>>
    suspend fun countCities(): Flow<Int>
    suspend fun getCityByNamePagingData(cityName: String): Flow<PagingData<CityEntity>>
    suspend fun saveLocalCities(citiesEntityList: List<CityEntity>)
}