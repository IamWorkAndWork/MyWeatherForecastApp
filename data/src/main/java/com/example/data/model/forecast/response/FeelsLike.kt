package com.example.data.model.forecast.response


import com.google.gson.annotations.SerializedName

data class FeelsLike(
    @SerializedName("day")
    val day: Double? = null,
    @SerializedName("eve")
    val eve: Double? = null,
    @SerializedName("morn")
    val morn: Double? = null,
    @SerializedName("night")
    val night: Double? = null
)