package com.example.data.mapper

import com.example.core.Constants
import com.example.core.ext.unixDateToDateTimeString
import com.example.core.model.DATE_TIME_FORMAT_yyyy_mm_dd
import com.example.core.model.ResultState
import com.example.core.model.map
import com.example.data.model.forecast.response.Daily
import com.example.data.model.forecast.response.ForecastResponseModel
import com.example.data.model.forecast.response.Hourly
import com.example.domain.model.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastResponseMapper {

    fun transform(
        resultModel: ResultState<ForecastResponseModel>,
        unit: String
    ): ResultState<ForecastModel> {

        return resultModel.map { model ->
            ForecastModel(
                lat = model.lat.toString(),
                lon = model.lon.toString(),
                timeZone = model.timezone.orEmpty(),
                unit = unit,
                hourlyModelList = mapToHourlyListModel(model.hourly),
                dailyModelList = mapToDailyListModel(model.daily)
            )
        }

    }

    private fun mapToDailyListModel(dailyList: List<Daily>?): List<DailyModel> {

        return dailyList?.map { daily ->

            val icon = daily.weather?.firstOrNull()?.icon.orEmpty()

            DailyModel(
                dateTime = daily.dt,
                sunriseTime = daily.sunrise,
                sunsetTime = daily.sunset,
                humidity = daily.humidity.toString(),
                pressure = daily.pressure.toString(),
                weatherDetailModel = WeatherDetailModel(
                    main = daily.weather?.firstOrNull()?.main.orEmpty(),
                    description = daily.weather?.firstOrNull()?.description.orEmpty(),
                    icon = icon,
                    fullIconUrl = String.format(Constants.ICON_PATH, icon)
                ),
                temperatureModel = TemperatureModel(
                    day = daily.temp?.day.toString(),
                    min = daily.temp?.min.toString(),
                    max = daily.temp?.max.toString(),
                    night = daily.temp?.night.toString(),
                    evening = daily.temp?.eve.toString(),
                    morning = daily.temp?.morn.toString()
                )
            )
        } ?: emptyList()

    }

    private fun mapToHourlyListModel(hourlyList: List<Hourly>?): List<HourlyModel> {

        val today = Calendar.getInstance().time
        val format = SimpleDateFormat(DATE_TIME_FORMAT_yyyy_mm_dd)
        val dateCurrent = format.format(today)

        return hourlyList?.map { hourly ->

            val icon = hourly.weather?.firstOrNull()?.icon.orEmpty()

            HourlyModel(
                dateTime = hourly.dt,
                temp = hourly.temp.toString(),
                humidity = hourly.humidity.toString(),
                clouds = hourly.humidity.toString(),
                wind_speed = hourly.windSpeed.toString(),
                pressure = hourly.pressure.toString(),
                weatherDetailModel = WeatherDetailModel(
                    main = hourly.weather?.firstOrNull()?.main.orEmpty(),
                    description = hourly.weather?.firstOrNull()?.description.orEmpty(),
                    icon = icon,
                    fullIconUrl = String.format(Constants.ICON_PATH, icon)
                )
            )
        }?.filter { hourly ->
            //filter only today
            val date = hourly.dateTime?.unixDateToDateTimeString(DATE_TIME_FORMAT_yyyy_mm_dd)
            dateCurrent == date
        } ?: emptyList()

    }

}