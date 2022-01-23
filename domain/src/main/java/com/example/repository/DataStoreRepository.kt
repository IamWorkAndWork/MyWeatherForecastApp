package com.example.repository

import com.example.domain.model.SettingModel
import kotlinx.coroutines.flow.Flow


interface DataStoreRepository {
    suspend fun saveAppSetting(
        unit: String,
        cityName: String,
        lat: String,
        lon: String
    )

    fun getSetting(): Flow<SettingModel>
}
