package com.example.data.model.country.response


import com.google.gson.annotations.SerializedName

data class CountryResponseModel(
    @SerializedName("data")
    val countriesList: List<CountryData>? = null,
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("msg")
    val msg: String? = null
)