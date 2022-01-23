package com.example.myweatherforecastapp.ui.home

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.example.myweatherforecastapp.*
import com.example.myweatherforecastapp.model.HomeUiModel
import com.example.myweatherforecastapp.model.SearchCityListener
import kotlin.properties.Delegates

/*
 * control Epoxy layout from EpoxyConfig with using *EpoxyDataBindingPattern
 */
class HomeUiController : EpoxyController() {

    var homeHiModelList by Delegates.observable(emptyList<HomeUiModel>()) { property, oldValue, newValue ->
        requestModelBuild()
    }

    private var searchCityListener: SearchCityListener? = null

    fun setSearchCityListener(searchCityListener: SearchCityListener) {
        this.searchCityListener = searchCityListener
    }

    override fun buildModels() {

        homeHiModelList.forEach { homeUiModel ->

            when (homeUiModel) {

                is HomeUiModel.CitySection -> {

                    renderSectionCity(homeUiModel)

                }
                is HomeUiModel.WeatherSection -> {

                    renderSectionWeather(homeUiModel)

                }
                is HomeUiModel.TitleSection -> {

                    renderSectionTitle(homeUiModel)

                }
                is HomeUiModel.DailyForecastSection -> {

                    renderSectionForecast(homeUiModel)

                }
                is HomeUiModel.Seperator -> {

                    renderSectionSeperator(homeUiModel)

                }

            }

        }

    }

    private fun renderSectionSeperator(homeUiModel: HomeUiModel.Seperator) {
        /*
         * commonSectionSeperator is from layout -> epoxy_common_section_seperator.xml
         */
        commonSectionSeperator {
            id(homeUiModel.id)
        }
    }

    private fun renderSectionForecast(homeUiModel: HomeUiModel.DailyForecastSection) {

        for (dailyModel in homeUiModel.forecastModel.dailyModelList) {

            /*
            * homeSectionDailyForecast is from layout -> epoxy_home_section_daily_forecast.xml
            */
            homeSectionDailyForecast {
                id(dailyModel.dateTime)
                dailyModel(dailyModel)
            }

        }

    }

    private fun renderSectionTitle(homeUiModel: HomeUiModel.TitleSection) {
        val titleString = homeUiModel.titleString

        /*
         * commonSectionTitle is from layout -> epoxy_common_section_title.xml
         */
        commonSectionTitle {
            id(titleString)
            titleString(titleString)
        }
    }

    private fun renderSectionWeather(homeUiModel: HomeUiModel.WeatherSection) {
        val weatherModel = homeUiModel.weatherModel

        /*
         * homeSectionWeather is from layout -> epoxy_home_section_weather.xml
         */
        homeSectionWeather {
            id(weatherModel.cityName)
            weatherModel(weatherModel)
        }
    }

    private fun renderSectionCity(homeUiModel: HomeUiModel.CitySection) {
        val cityName = homeUiModel.cityName

        /*
         * commonSectionCity is from layout -> epoxy_common_section_city.xml
         */
        searchCityListener?.let { listener ->
            commonSectionCity {
                id(cityName)
                cityName(cityName)
                searchCityListener(listener)
            }
        }
    }

    fun onSearchCityClick(view: View) {
        searchCityListener?.onSearchCity(view)
    }

}