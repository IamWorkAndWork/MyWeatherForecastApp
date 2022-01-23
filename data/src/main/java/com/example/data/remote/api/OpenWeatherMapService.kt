package com.example.data.remote.api

import com.example.data.model.forecast.response.ForecastResponseModel
import com.example.data.model.weather.response.WeatherResponseModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface OpenWeatherMapService {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @QueryMap request: Map<String, String>
    ): WeatherResponseModel

    @GET("data/2.5/onecall")
    suspend fun getForecast(
        @QueryMap request: Map<String, String>
    ): ForecastResponseModel

    @GET("data/2.5/onecall/timemachine")
    suspend fun getTimeMachineForcast(
        @QueryMap request: Map<String, String>
    ): ForecastResponseModel

}