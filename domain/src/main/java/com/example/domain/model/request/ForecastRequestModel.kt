package com.example.domain.model.request

data class ForecastRequestModel(
    val lat: String,
    val lon: String,
    val exclude: String = "minutely",
    val units: String
)

fun ForecastRequestModel.toQueryMap(): Map<String, String> {
    return mapOf(
        "lat" to lat,
        "lon" to lon,
        "exclude" to exclude,
        "units" to units
    )
}