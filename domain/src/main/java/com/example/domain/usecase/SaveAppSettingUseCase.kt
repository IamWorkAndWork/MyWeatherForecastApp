package com.example.domain.usecase

import com.example.repository.DataStoreRepository

interface SaveAppSettingUseCase {
    suspend fun execute(
        unit: String,
        cityName: String,
        lat: String,
        lon: String
    )
}

class SaveAppSettingUseCaseImpl(
    private val dataStoreRepository: DataStoreRepository
) : SaveAppSettingUseCase {

    override suspend fun execute(
        unit: String,
        cityName: String,
        lat: String,
        lon: String
    ) {

        dataStoreRepository.saveAppSetting(unit = unit, cityName = cityName, lat = lat, lon = lon)

    }

}