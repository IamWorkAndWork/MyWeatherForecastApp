package com.example.data.model.forecast.response


import com.google.gson.annotations.SerializedName

data class ForecastResponseModel(
    @SerializedName("current")
    val current: Current? = null,
    @SerializedName("daily")
    val daily: List<Daily>? = null,
    @SerializedName("hourly")
    val hourly: List<Hourly>? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null,
    @SerializedName("timezone")
    val timezone: String? = null,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int? = null
)