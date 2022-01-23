package com.example.data.repository

import com.example.core.model.ResultState
import com.example.core.utils.BaseUnitTest
import com.example.data.mapper.ForecastResponseMapper
import com.example.data.mapper.WeatherResponseMapper
import com.example.data.model.weather.response.WeatherResponseModel
import com.example.data.remote.OpenWeatherRemoteDataSource
import com.example.domain.model.WeatherDetailModel
import com.example.domain.model.WeatherModel
import com.example.domain.model.request.WeatherRequestModel
import com.example.repository.OpenWeatherRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OpenWeatherRepositoryImplTest : BaseUnitTest() {

    private val remoteDataSource: OpenWeatherRemoteDataSource = mock()
    private val weatherResponseMapper: WeatherResponseMapper = WeatherResponseMapper()
    private val forecastResponseMapper: ForecastResponseMapper = ForecastResponseMapper()

    private lateinit var openWeatherRepository: OpenWeatherRepository

    @Before
    fun setUp() {

        openWeatherRepository = OpenWeatherRepositoryImpl(
            remoteDataSource = remoteDataSource,
            weatherResponseMapper = weatherResponseMapper,
            forecastResponseMapper = forecastResponseMapper
        )

    }

    @Test
    fun `test success get data from remote API`() = runTest {

        //given
        val weatherModel = WeatherModel(
            lat = "1",
            lon = "2",
            temprature = "",
            minTemprature = "",
            maxTemprature = "",
            humidity = "",
            cityName = "",
            unit = "test",
            weatherDetailModel = WeatherDetailModel(
                main = "",
                description = "",
                icon = "",
                fullIconUrl = ""
            )
        )
        val weatherResponseModel = WeatherResponseModel(dt = 1234, name = "test")
        val result: ResultState<WeatherResponseModel> = ResultState.Success(weatherResponseModel)

        val request = WeatherRequestModel(
            query = "",
            units = ""
        )
        //when
        whenever(
            remoteDataSource.getWeather(
                request
            )
        ).thenReturn(
            flow {
                emit(result)
            }
        )

        //then
        val response = openWeatherRepository.getWeather(request)

        response.collectLatest {
            if (it is ResultState.Success) {
                assertEquals(it.data.cityName, "test")
            }
        }

    }
}