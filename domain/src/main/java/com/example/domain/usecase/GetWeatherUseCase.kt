package com.example.domain.usecase

import com.example.core.model.ResultState
import com.example.domain.model.WeatherModel
import com.example.domain.model.request.WeatherRequestModel
import com.example.domain.model.request.toQueryMap
import com.example.repository.OpenWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface GetWeatherUseCase {
    fun execute(weatherRequestModel: WeatherRequestModel): Flow<ResultState<WeatherModel>>
}

class GetWeatherUseCaseImpl(
    private val openWeatherRepository: OpenWeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetWeatherUseCase {

    override fun execute(weatherRequestModel: WeatherRequestModel): Flow<ResultState<WeatherModel>> {

        return openWeatherRepository.getWeather(
            request = weatherRequestModel
        ).flowOn(dispatcher)

    }

}