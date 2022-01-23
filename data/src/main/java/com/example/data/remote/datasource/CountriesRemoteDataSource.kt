package com.example.data.remote

import com.example.core.model.ResultState
import com.example.data.remote.api.CountriesNowAPI
import com.example.data.model.country.response.CountryResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CountriesRemoteDataSource {
    fun getCountries(): Flow<ResultState<CountryResponseModel>>
}

class CountriesRemoteDataSourceImpl(
    private val countriesNowService: CountriesNowAPI
) : CountriesRemoteDataSource {

    override fun getCountries(): Flow<ResultState<CountryResponseModel>> = flow {
        try {

            val response = countriesNowService.getCountries()
            emit(ResultState.Success(response))

        } catch (error: Exception) {

            emit(ResultState.Error(error))

        }
    }

}