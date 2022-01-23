package com.example.data.providers

import android.content.Context
import com.example.data.remote.api.CountriesNowAPI
import com.example.data.remote.api.OpenWeatherMapService
import com.example.data.local.database.AppDatabase
import com.example.data.mapper.CountryResponseMapper
import com.example.data.mapper.ForecastResponseMapper
import com.example.data.mapper.WeatherResponseMapper
import com.example.data.remote.CountriesRemoteDataSource
import com.example.data.remote.CountriesRemoteDataSourceImpl
import com.example.data.remote.OpenWeatherRemoteDataSource
import com.example.data.remote.OpenWeatherRemoteDataSourceImpl
import com.example.data.repository.CountriesRepositoryImpl
import com.example.data.repository.DataStoreRepositoryImpl
import com.example.data.repository.OpenWeatherRepositoryImpl
import com.example.repository.CountriesRepository
import com.example.repository.DataStoreRepository
import com.example.repository.OpenWeatherRepository
import kotlinx.coroutines.FlowPreview

fun provideCountriesRemoteDataSource(
    countriesNowService: CountriesNowAPI
): CountriesRemoteDataSource {
    return CountriesRemoteDataSourceImpl(
        countriesNowService = countriesNowService
    )
}

fun provideOpenWeatherRemoteDataSourceImpl(
    openWeatherMapService: OpenWeatherMapService
): OpenWeatherRemoteDataSource {
    return OpenWeatherRemoteDataSourceImpl(
        openWeatherMapService = openWeatherMapService
    )
}

@FlowPreview
fun provideCountriesRepository(
    countriesRemoteDataSource: CountriesRemoteDataSource,
    appDatabase: AppDatabase,
    countryResponseMapper: CountryResponseMapper
): CountriesRepository {
    return CountriesRepositoryImpl(
        remoteDataSource = countriesRemoteDataSource,
        localDataSource = appDatabase,
        countryResponseMapper = countryResponseMapper
    )
}

fun provideDataStoreRepository(context: Context): DataStoreRepository {
    return DataStoreRepositoryImpl(context = context)
}

fun provideOpenWeatherRepository(
    openWeatherRemoteDataSource: OpenWeatherRemoteDataSource,
    weatherResponseMapper: WeatherResponseMapper,
    forecastResponseMapper: ForecastResponseMapper
): OpenWeatherRepository {
    return OpenWeatherRepositoryImpl(
        remoteDataSource = openWeatherRemoteDataSource,
        weatherResponseMapper = weatherResponseMapper,
        forecastResponseMapper = forecastResponseMapper
    )
}