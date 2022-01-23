package com.example.domain.model.request

data class TimeMachineForecastRequestModel(
    val lat: String,
    val lon: String,
    val dateTime: String,
    val units: String
)

fun TimeMachineForecastRequestModel.toQueryMap(): Map<String, String> {
    return mapOf(
        "lat" to lat,
        "lon" to lon,
        "dt" to dateTime,
        "units" to units
    )
}