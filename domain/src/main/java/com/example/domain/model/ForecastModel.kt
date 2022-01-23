package com.example.domain.model

data class ForecastModel(
    val lat: String,
    val lon: String,
    val timeZone: String,
    val unit: String,
    val hourlyModelList: List<HourlyModel> = emptyList(),
    val dailyModelList: List<DailyModel> = emptyList(),
)

data class HourlyModel(
    val dateTime: Int? = null,
    val temp: String,
    val humidity: String,
    val clouds: String,
    val wind_speed: String,
    val pressure: String,
    val weatherDetailModel: WeatherDetailModel
)

data class DailyModel(
    val dateTime: Int? = null,
    val sunriseTime: Int? = null,
    val sunsetTime: Int? = null,
    val pressure: String,
    val humidity: String,
    val weatherDetailModel: WeatherDetailModel,
    val temperatureModel: TemperatureModel
)

data class TemperatureModel(
    val day: String,
    val min: String,
    val max: String,
    val night: String,
    val evening: String,
    val morning: String
)