package com.example.myweatherforecastapp.ui.home

import android.content.Context
import com.example.core.model.ResultState
import com.example.core.utils.BaseUnitTest
import com.example.domain.model.ForecastModel
import com.example.domain.model.SettingModel
import com.example.domain.model.WeatherDetailModel
import com.example.domain.model.WeatherModel
import com.example.domain.model.request.ForecastRequestModel
import com.example.domain.model.request.WeatherRequestModel
import com.example.domain.usecase.GetAppSettingUseCase
import com.example.domain.usecase.GetForecastUseCase
import com.example.domain.usecase.GetWeatherUseCase
import com.example.myweatherforecastapp.mapper.ResultToHomeUiModelMapper
import com.example.myweatherforecastapp.model.HomeUiModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : BaseUnitTest() {

    @Mock
    private lateinit var mockContext: Context

    private val getAppSettingUseCase: GetAppSettingUseCase = mock()
    private val getWeatherUseCase: GetWeatherUseCase = mock()
    private val getForecastUseCase: GetForecastUseCase = mock()
    private lateinit var resultToHomeUiModelMapper: ResultToHomeUiModelMapper

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {

        resultToHomeUiModelMapper = ResultToHomeUiModelMapper(mockContext)

        homeViewModel = HomeViewModel(
            getAppSettingUseCase, getWeatherUseCase, getForecastUseCase, resultToHomeUiModelMapper
        )
    }

    @Test
    fun `test load uiHome Success`() = runTest {

        //given
        val weaherModel = getWeatherModel()
        val settingModel = SettingModel(
            cityName = "test",
            unit = "tset2",
            lat = "1",
            lon = "2"
        )

        val resultsHomeUiModel = mutableListOf<ResultState<List<HomeUiModel>>>()
        val resultsSettingModel = mutableListOf<SettingModel>()

        //when
        val job = launch {
            homeViewModel.appSettingModel.toList(resultsSettingModel)
        }
        val jobHomeUiModel = launch {
            homeViewModel.homeUiModel.toList(resultsHomeUiModel)
        }

        whenever(
            getWeatherUseCase.execute(
                weatherRequestModel = getWeatherRequest()
            )
        ).thenReturn(
            flow {
                emit(ResultState.Success(weaherModel))
            }
        )

        whenever(
            getForecastUseCase.execute(
                request = getForecastRequest()
            )
        ).thenReturn(
            flow {
                emit(ResultState.Success(getForecastModel()))
            }
        )

        //then
        homeViewModel.loadHomeUiData()

        println("resultsHomeUiModel= ${resultsHomeUiModel.size}")
        println("resultsSettingModel= ${resultsSettingModel.size}")


        job.cancel()
        jobHomeUiModel.cancel()
    }

    private fun getForecastRequest(): ForecastRequestModel {
        return ForecastRequestModel(
            "", "", "", "unit"
        )
    }

    private fun getWeatherRequest(): WeatherRequestModel {
        return WeatherRequestModel(
            "query", "unit"
        )
    }

    private fun getForecastModel(): ForecastModel {
        return ForecastModel(
            lat = "",
            lon = "",
            timeZone = "",
            unit = "",
            hourlyModelList = listOf(),
            dailyModelList = listOf()
        )
    }

    private fun getWeatherModel(): WeatherModel {
        return WeatherModel(
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
    }

}