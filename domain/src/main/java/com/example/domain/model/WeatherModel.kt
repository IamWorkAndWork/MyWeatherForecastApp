package com.example.domain.model

data class WeatherModel(
    val lat: String = "",
    val lon: String = "",
    val temprature: String = "",
    val minTemprature: String = "",
    val maxTemprature: String = "",
    val humidity: String = "",
    val cityName: String = "",
    val unit: String = "",
    val weatherDetailModel: WeatherDetailModel
)

data class WeatherDetailModel(
    val main: String = "",
    val description: String = "",
    val icon: String = "",
    val fullIconUrl: String = "",
)
