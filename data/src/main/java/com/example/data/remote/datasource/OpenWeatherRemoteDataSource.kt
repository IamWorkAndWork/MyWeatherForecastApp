package com.example.data.remote

import com.example.core.model.ResultState
import com.example.data.remote.api.OpenWeatherMapService
import com.example.data.model.forecast.response.ForecastResponseModel
import com.example.data.model.weather.response.WeatherResponseModel
import com.example.domain.model.request.ForecastRequestModel
import com.example.domain.model.request.TimeMachineForecastRequestModel
import com.example.domain.model.request.WeatherRequestModel
import com.example.domain.model.request.toQueryMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface OpenWeatherRemoteDataSource {

    fun getWeather(request: WeatherRequestModel): Flow<ResultState<WeatherResponseModel>>

    fun getForecast(request: ForecastRequestModel): Flow<ResultState<ForecastResponseModel>>

    fun getTimeMachineForcast(request: TimeMachineForecastRequestModel): Flow<ResultState<ForecastResponseModel>>

}

class OpenWeatherRemoteDataSourceImpl(
    private val openWeatherMapService: OpenWeatherMapService
) : OpenWeatherRemoteDataSource {

    override fun getWeather(request: WeatherRequestModel) = flow {
        try {

            val response = openWeatherMapService.getWeather(request = request.toQueryMap())
            emit(ResultState.Success(response))

        } catch (error: Exception) {

            emit(ResultState.Error(error))

        }
    }

    override fun getForecast(request: ForecastRequestModel) = flow {
        try {

            val response = openWeatherMapService.getForecast(request = request.toQueryMap())
            emit(ResultState.Success(response))

        } catch (error: Exception) {

            emit(ResultState.Error(error))

        }
    }

    override fun getTimeMachineForcast(request: TimeMachineForecastRequestModel) = flow {
        try {

            val response = openWeatherMapService.getTimeMachineForcast(request = request.toQueryMap())
            emit(ResultState.Success(response))

        } catch (error: Exception) {

            emit(ResultState.Error(error))

        }
    }

}