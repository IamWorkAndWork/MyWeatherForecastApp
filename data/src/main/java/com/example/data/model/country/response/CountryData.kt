package com.example.data.model.country.response


import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("cities")
    val cities: List<String>? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("iso2")
    val iso2: String? = null,
    @SerializedName("iso3")
    val iso3: String? = null
)