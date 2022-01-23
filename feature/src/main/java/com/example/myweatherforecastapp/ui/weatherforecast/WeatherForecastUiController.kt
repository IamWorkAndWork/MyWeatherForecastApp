package com.example.myweatherforecastapp.ui.weatherforecast

import com.airbnb.epoxy.EpoxyController
import com.example.myweatherforecastapp.commonSectionCity
import com.example.myweatherforecastapp.commonSectionSeperator
import com.example.myweatherforecastapp.commonSectionTitle
import com.example.myweatherforecastapp.model.ForecastUiModel
import com.example.myweatherforecastapp.model.SearchCityListener
import com.example.myweatherforecastapp.weatherForecastItem
import kotlin.properties.Delegates

class WeatherForecastUiController : EpoxyController() {

    var forecastUiModel by Delegates.observable(null as ForecastUiModel?) { property, oldValue, newValue ->
        requestModelBuild()
    }

    private var searchCityListener: SearchCityListener? = null

    fun setSearchCityListener(searchCityListener: SearchCityListener) {
        this.searchCityListener = searchCityListener
    }

    override fun buildModels() {

        if (forecastUiModel != null) {

            val cityName = forecastUiModel?.cityName
            val todayDate = forecastUiModel?.todayDate

            searchCityListener?.let { listener ->
                commonSectionCity {
                    id(cityName)
                    cityName(cityName)
                    searchCityListener(listener)
                }
            }

            commonSectionTitle {
                id(todayDate)
                titleString(todayDate)
            }

            forecastUiModel?.forecastModel?.let { forecastUiModel ->

                for (model in forecastUiModel.hourlyModelList) {

                    weatherForecastItem {
                        id(model.dateTime)
                        hourlyModel(model)
                    }


                }

            }

            commonSectionSeperator {
                id(cityName)
            }

        }

    }

}