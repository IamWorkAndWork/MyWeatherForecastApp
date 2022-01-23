package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.CityEntity
import com.example.repository.CountriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface GetCityListByNameUseCase {
    suspend fun execute(cityName: String): Flow<PagingData<CityEntity>>
}

class GetCityListByNameUseCaseImpl(
    private val countriesRepository: CountriesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetCityListByNameUseCase {

    override suspend fun execute(cityName: String): Flow<PagingData<CityEntity>> {

        val queryCityName = "%${cityName}%"

        return countriesRepository.getCityByNamePagingData(cityName = queryCityName)
            .flowOn(dispatcher)

    }

}