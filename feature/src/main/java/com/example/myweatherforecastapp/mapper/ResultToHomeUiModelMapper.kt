package com.example.myweatherforecastapp.mapper

import android.content.Context
import com.example.core.model.ResultState
import com.example.core.model.map
import com.example.domain.model.ForecastModel
import com.example.domain.model.WeatherModel
import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.model.HomeUiModel
import java.util.*

class ResultToHomeUiModelMapper(
    private val context: Context
) {

    fun transform(
        weatherModel: ResultState<WeatherModel>,
        forecastModel: ResultState<ForecastModel>
    ): ResultState<List<HomeUiModel>> {

        checkException(weatherModel, forecastModel)

        /*
         * control the sequence of UI to render in HomeFragment
         * we sort the order of the layout that we want here.
         * example sequence of UI in HomeFragment;
         *      CitySection
         *      WeatherSection
         *      Seperator
         *      TitleSection
         *      DailyForecastSection
         */

        val homeUiModelList = mutableListOf<HomeUiModel>()

        weatherModel.map { model ->

            homeUiModelList.add(
                HomeUiModel.CitySection(
                    model.cityName
                )
            )

            homeUiModelList.add(
                HomeUiModel.WeatherSection(
                    weatherModel = model
                )
            )

        }

        forecastModel.map { model ->

            homeUiModelList.add(
                HomeUiModel.TitleSection(
                    titleString = context.getString(R.string.home_title_weekly_forecast)
                )
            )

            homeUiModelList.add(
                HomeUiModel.DailyForecastSection(
                    forecastModel = model
                )
            )

        }

        homeUiModelList.add(
            HomeUiModel.Seperator(id = Date().time)
        )

        return ResultState.Success(homeUiModelList)

    }

    fun checkException(
        weatherModel: ResultState<WeatherModel>,
        forecastModel: ResultState<ForecastModel>
    ) {
        if (weatherModel is ResultState.Error) {
            throw weatherModel.exception
        }
        if (forecastModel is ResultState.Error) {
            throw forecastModel.exception
        }
    }

}