package com.example.myweatherforecastapp.di

import com.example.myweatherforecastapp.ui.bottomsheet.SearchCityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bottomSheetModule = module {

    viewModel {
        SearchCityViewModel(
            getCityListByNameUseCase = get(),
            saveAppSettingUseCase = get(),
            getAppSettingUseCase = get(),
            getWeatherUseCase = get(),
            loadCitiesUseCase = get()
        )
    }

}