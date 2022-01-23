package com.example.di

import com.example.data.providers.*
import com.example.data.remote.CountriesRemoteDataSource
import com.example.data.remote.OpenWeatherRemoteDataSource
import com.example.repository.CountriesRepository
import com.example.repository.DataStoreRepository
import com.example.repository.OpenWeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repositoryModule = module {

    single<CountriesRepository> {
        provideCountriesRepository(
            countryResponseMapper = get(),
            countriesRemoteDataSource = get(),
            appDatabase = get()
        )
    }

    single<DataStoreRepository> {
        provideDataStoreRepository(
            context = androidContext()
        )
    }

    single<OpenWeatherRepository> {
        provideOpenWeatherRepository(
            openWeatherRemoteDataSource = get(),
            weatherResponseMapper = get(),
            forecastResponseMapper = get()
        )
    }

    single<CountriesRemoteDataSource> {
        provideCountriesRemoteDataSource(
            countriesNowService = get()
        )
    }

    single<OpenWeatherRemoteDataSource> {
        provideOpenWeatherRemoteDataSourceImpl(
            openWeatherMapService = get()
        )
    }

}