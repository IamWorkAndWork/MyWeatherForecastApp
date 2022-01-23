package com.example.myweatherforecastapp.model

import com.example.domain.model.ForecastModel
import com.example.domain.model.WeatherModel

sealed class HomeUiModel {

    /*
     * this model is for control the UI with difference Layout
     * and assign used model for that layout
     */

    data class CitySection(
        val cityName: String
    ) : HomeUiModel()

    data class WeatherSection(
        val weatherModel: WeatherModel
    ) : HomeUiModel()

    data class DailyForecastSection(
        val forecastModel: ForecastModel
    ) : HomeUiModel()

    data class TitleSection(
        val titleString: String
    ) : HomeUiModel()

    data class Seperator(
        val id: Long
    ) : HomeUiModel()

}