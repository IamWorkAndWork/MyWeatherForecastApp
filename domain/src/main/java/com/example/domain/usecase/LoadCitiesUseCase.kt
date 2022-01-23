package com.example.domain.usecase

import com.example.domain.model.CityEntity
import com.example.repository.CountriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface LoadCitiesUseCase {
    suspend fun execute(): Flow<List<CityEntity>>
}

@ExperimentalCoroutinesApi
class LoadCitiesUseCaseImpl(
    private val countriesRepository: CountriesRepository
) : LoadCitiesUseCase {

    override suspend fun execute(): Flow<List<CityEntity>> {

        return countriesRepository.countCities()
            .map { countAllLocalCities ->
                countAllLocalCities == 0
            }
            .flatMapLatest { isNeedToFetchFromRemote ->

                if (isNeedToFetchFromRemote) {
                    countriesRepository.getRemoteCitiesList()
                } else {
                    flowOf(listOf())
                }

            }

    }

}