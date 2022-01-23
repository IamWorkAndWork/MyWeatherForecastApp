package com.example.data.mapper

import com.example.core.Constants.ICON_PATH
import com.example.core.model.ResultState
import com.example.core.model.map
import com.example.data.model.weather.response.WeatherResponseModel
import com.example.domain.model.WeatherDetailModel
import com.example.domain.model.WeatherModel

class WeatherResponseMapper {

    fun transform(
        resultModel: ResultState<WeatherResponseModel>,
        unit: String
    ): ResultState<WeatherModel> {
        return resultModel.map { model ->

            val icon = model.weather?.firstOrNull()?.icon.orEmpty()

            WeatherModel(
                lat = model.coord?.lat?.toString().orEmpty(),
                lon = model.coord?.lon?.toString().orEmpty(),
                temprature = model.main?.temp.toString(),
                minTemprature = model.main?.tempMin.toString(),
                maxTemprature = model.main?.tempMax.toString(),
                humidity = model.main?.humidity.toString(),
                cityName = model.name.orEmpty(),
                unit = unit,
                weatherDetailModel = WeatherDetailModel(
                    main = model.weather?.firstOrNull()?.main.orEmpty(),
                    description = model.weather?.firstOrNull()?.description.orEmpty(),
                    icon = icon,
                    fullIconUrl = String.format(ICON_PATH, icon)
                )
            )
        }

    }

}