package com.example.domain.usecase

import com.example.core.model.ResultState
import com.example.domain.model.ForecastModel
import com.example.domain.model.request.ForecastRequestModel
import com.example.repository.OpenWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface GetForecastUseCase {
    fun execute(request: ForecastRequestModel): Flow<ResultState<ForecastModel>>
}

class GetForecastUseCaseImpl(
    private val openWeatherRepository: OpenWeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetForecastUseCase {

    override fun execute(request: ForecastRequestModel): Flow<ResultState<ForecastModel>> {

        return openWeatherRepository.getForecast(request = request)
            .flowOn(dispatcher)

    }

}