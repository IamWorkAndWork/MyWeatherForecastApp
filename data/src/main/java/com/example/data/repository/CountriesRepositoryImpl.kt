package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.local.database.AppDatabase
import com.example.data.mapper.CountryResponseMapper
import com.example.data.remote.CountriesRemoteDataSource
import com.example.domain.model.CityEntity
import com.example.repository.CountriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class CountriesRepositoryImpl(
    private val remoteDataSource: CountriesRemoteDataSource,
    private val localDataSource: AppDatabase,
    private val countryResponseMapper: CountryResponseMapper
) : CountriesRepository {

    private val cityDao = localDataSource.cityDao()

    companion object {
        private const val PAGE_SIZE = 50
    }

    override fun getRemoteCitiesList(): Flow<List<CityEntity>> {

        return remoteDataSource.getCountries()
            .map { resultModel ->
                countryResponseMapper.transform(resultModel)
            }.onEach { citiesEntityList ->
                saveLocalCities(citiesEntityList = citiesEntityList)
            }

    }

    override suspend fun saveLocalCities(citiesEntityList: List<CityEntity>) {

        cityDao.insertAll(citiesEntityList = citiesEntityList)

    }

    override suspend fun countCities(): Flow<Int> {

        return flowOf(cityDao.countCities())

    }

    override suspend fun getCityByNamePagingData(cityName: String): Flow<PagingData<CityEntity>> {

        return getCitiesPagingData(cityName)

    }

    private fun getCitiesPagingData(cityName: String): Flow<PagingData<CityEntity>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {

                cityDao.getCityByName(cityName)

            }
        ).flow

    }

}