package com.example.myweatherforecastapp.di

import com.example.myweatherforecastapp.ui.weatherforecast.WeatherForecastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherForecastModule = module {

    viewModel {
        WeatherForecastViewModel(
            getTodayForecastUseCase = get(),
            getAppSettingUseCase = get()
        )
    }

}