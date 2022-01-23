package com.example.data.mapper

import com.example.core.model.ResultState
import com.example.core.model.map
import com.example.data.model.country.response.CountryResponseModel
import com.example.domain.model.CityEntity

class CountryResponseMapper {
    fun transform(countryResponseModel: ResultState<CountryResponseModel>): List<CityEntity> {

        val citiesList = mutableListOf<CityEntity>()

        countryResponseModel.map { modelResponse ->
            modelResponse.countriesList?.forEach { countryData ->
                countryData.cities?.forEach { city ->
                    citiesList.add(
                        CityEntity(
                            country = countryData.country.orEmpty(),
                            city = city,
                            iso2 = countryData.iso2.orEmpty(),
                            iso3 = countryData.iso3.orEmpty()
                        )
                    )
                }
            }
        }

        return citiesList
    }
}