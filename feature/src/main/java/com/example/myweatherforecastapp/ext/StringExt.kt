package com.example.myweatherforecastapp.ext

import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.model.UnitType

fun String.UnitTypeToTemperatureIdText(): Int {
    return when (this) {
        UnitType.Celsius.unit -> {
            R.string.label_celsius
        }
        UnitType.Fahrenheit.unit -> {
            R.string.label_fahrenheit
        }
        else -> {
            R.string.label_celsius
        }
    }
}