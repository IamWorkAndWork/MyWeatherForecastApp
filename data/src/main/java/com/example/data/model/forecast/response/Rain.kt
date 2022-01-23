package com.example.data.model.forecast.response


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double? = null
)