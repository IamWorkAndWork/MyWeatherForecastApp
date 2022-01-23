package com.example.di

import com.example.data.mapper.CountryResponseMapper
import com.example.data.mapper.ForecastResponseMapper
import com.example.data.mapper.WeatherResponseMapper
import org.koin.dsl.module

val mapperModule = module {

    factory {
        CountryResponseMapper()
    }

    factory {
        WeatherResponseMapper()
    }

    factory {
        ForecastResponseMapper()
    }

}