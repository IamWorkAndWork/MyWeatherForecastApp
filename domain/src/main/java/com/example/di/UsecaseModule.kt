package com.example.di

import com.example.domain.usecase.*
import com.example.domain.usecase.forecast.GetTodayForecastUseCase
import com.example.domain.usecase.forecast.GetTodayForecastUseCaseImpl
import org.koin.dsl.module

val usecaseModule = module {

    factory<GetCityListByNameUseCase> {
        GetCityListByNameUseCaseImpl(
            countriesRepository = get()
        )
    }

    factory<GetAppSettingUseCase> {
        GetAppSettingUseCaseImpl(
            dataStoreRepository = get()
        )
    }

    factory<SaveAppSettingUseCase> {
        SaveAppSettingUseCaseImpl(
            dataStoreRepository = get()
        )
    }

    factory<GetWeatherUseCase> {
        GetWeatherUseCaseImpl(
            openWeatherRepository = get()
        )
    }

    factory<LoadCitiesUseCase> {
        LoadCitiesUseCaseImpl(
            countriesRepository = get()
        )
    }

    factory<GetForecastUseCase> {
        GetForecastUseCaseImpl(
            openWeatherRepository = get()
        )
    }

    factory<GetTimeMachineForecastUseCase> {
        GetTimeMachineForecastUseCaseImpl(
            openWeatherRepository = get()
        )
    }

    factory<GetTodayForecastUseCase> {
        GetTodayForecastUseCaseImpl(
            getForecastUseCase = get(),
            getTimeMachineForecastUseCase = get()
        )
    }

}

