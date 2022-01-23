package com.example.data.repository

import com.example.core.model.ResultState
import com.example.data.mapper.ForecastResponseMapper
import com.example.data.mapper.WeatherResponseMapper
import com.example.data.remote.OpenWeatherRemoteDataSource
import com.example.domain.model.ForecastModel
import com.example.domain.model.WeatherModel
import com.example.domain.model.request.ForecastRequestModel
import com.example.domain.model.request.TimeMachineForecastRequestModel
import com.example.domain.model.request.WeatherRequestModel
import com.example.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OpenWeatherRepositoryImpl(
    private val remoteDataSource: OpenWeatherRemoteDataSource,
    private val weatherResponseMapper: WeatherResponseMapper,
    private val forecastResponseMapper: ForecastResponseMapper
) : OpenWeatherRepository {

    override fun getWeather(request: WeatherRequestModel): Flow<ResultState<WeatherModel>> {

        return remoteDataSource.getWeather(request = request)
            .map { resultModel ->
                weatherResponseMapper.transform(resultModel = resultModel, unit = request.units)
            }

    }

    override fun getForecast(request: ForecastRequestModel): Flow<ResultState<ForecastModel>> {

        return remoteDataSource.getForecast(request = request)
            .map { resultModel ->
                forecastResponseMapper.transform(resultModel = resultModel, unit = request.units)
            }

    }

    override fun getTimeMachineForcast(request: TimeMachineForecastRequestModel): Flow<ResultState<ForecastModel>> {

        return remoteDataSource.getTimeMachineForcast(request = request)
            .map { resultModel ->
                forecastResponseMapper.transform(resultModel = resultModel, unit = request.units)
            }

    }

}