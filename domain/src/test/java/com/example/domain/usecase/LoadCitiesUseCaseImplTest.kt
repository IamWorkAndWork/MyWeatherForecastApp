package com.example.domain.usecase

import com.example.core.utils.BaseUnitTest
import com.example.domain.model.CityEntity
import com.example.repository.CountriesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadCitiesUseCaseImplTest : BaseUnitTest() {

    private val countriesRepository: CountriesRepository = mock()

    private lateinit var loadCitiesUseCase: LoadCitiesUseCase

    @Before
    fun setup() {

        loadCitiesUseCase = LoadCitiesUseCaseImpl(
            countriesRepository = countriesRepository
        )

    }

    @Test
    fun `test load city 1st time should fect from remote`() = runTest {

        //given
        val countCityLocal = 0
        val cityList =
            listOf<CityEntity>(CityEntity(country = "1", city = "2", iso2 = "3", iso3 = "4"))

        //when
        whenever(countriesRepository.countCities()).thenReturn(
            flow {
                emit(countCityLocal)
            }
        )

        whenever(countriesRepository.getRemoteCitiesList()).thenReturn(
            flow {
                emit(cityList)
            }
        )

        //then
        val result = loadCitiesUseCase.execute()

        assertEquals(result.first(), cityList)
        assertEquals(result.toList().size, 1)

    }

}