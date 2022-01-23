package com.example.data.remote.api

import com.example.data.model.country.response.CountryResponseModel
import retrofit2.http.GET

interface CountriesNowAPI {

    @GET("api/v0.1/countries")
    suspend fun getCountries(): CountryResponseModel

}