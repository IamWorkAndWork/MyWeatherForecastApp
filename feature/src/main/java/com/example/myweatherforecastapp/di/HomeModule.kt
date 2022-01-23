package com.example.myweatherforecastapp.di

import com.example.domain.usecase.LoadCitiesUseCase
import com.example.myweatherforecastapp.mapper.ResultToHomeUiModelMapper
import com.example.myweatherforecastapp.ui.home.HomeViewModel
import com.example.myweatherforecastapp.ui.settings.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val homeModule = module {

    factory<ResultToHomeUiModelMapper> {
        ResultToHomeUiModelMapper(
            context = get()
        )
    }

    viewModel {
        HomeViewModel(
            getAppSettingUseCase = get(),
            getWeatherUseCase = get(),
            getForecastUseCase = get(),
            resultToHomeUiModelMapper = get()
        )
    }

    viewModel {
        SettingsViewModel(
            getAppSettingUseCase = get(),
            saveAppSettingUseCase = get()
        )
    }

}