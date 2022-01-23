package com.example.domain.usecase

import com.example.core.model.ResultState
import com.example.domain.model.ForecastModel
import com.example.domain.model.request.TimeMachineForecastRequestModel
import com.example.repository.OpenWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface GetTimeMachineForecastUseCase {
    fun execute(request: TimeMachineForecastRequestModel): Flow<ResultState<ForecastModel>>
}

class GetTimeMachineForecastUseCaseImpl(
    private val openWeatherRepository: OpenWeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetTimeMachineForecastUseCase {

    override fun execute(request: TimeMachineForecastRequestModel): Flow<ResultState<ForecastModel>> {

        return openWeatherRepository.getTimeMachineForcast(request = request)
            .flowOn(dispatcher)

    }

}