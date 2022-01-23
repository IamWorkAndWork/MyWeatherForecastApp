package com.example.repository

import com.example.core.model.ResultState
import com.example.domain.model.ForecastModel
import com.example.domain.model.WeatherModel
import com.example.domain.model.request.ForecastRequestModel
import com.example.domain.model.request.TimeMachineForecastRequestModel
import com.example.domain.model.request.WeatherRequestModel
import kotlinx.coroutines.flow.Flow

interface OpenWeatherRepository {
    fun getWeather(request: WeatherRequestModel): Flow<ResultState<WeatherModel>>

    fun getForecast(request: ForecastRequestModel): Flow<ResultState<ForecastModel>>

    fun getTimeMachineForcast(request: TimeMachineForecastRequestModel): Flow<ResultState<ForecastModel>>
}