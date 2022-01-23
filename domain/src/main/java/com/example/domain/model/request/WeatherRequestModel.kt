package com.example.domain.model.request

data class WeatherRequestModel(
    val query: String,
    val units: String
)

fun WeatherRequestModel.toQueryMap(): Map<String, String> {
    return mapOf(
        "q" to query,
        "units" to units
    )
}