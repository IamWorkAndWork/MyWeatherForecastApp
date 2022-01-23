package com.example.myweatherforecastapp.model

import com.example.domain.model.ForecastModel

data class ForecastUiModel(
    val forecastModel: ForecastModel,
    val cityName: String,
    val todayDate: String
)