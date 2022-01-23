package com.example.core.ext

import java.text.SimpleDateFormat
import java.util.*

fun Int.unixDateToDateTimeString(format: String): String {
    val dateTimeFormat = SimpleDateFormat(format)
    val date = Date(this.toLong() * 1000)
    return dateTimeFormat.format(date)
}

fun Long.unixDateToDateTimeString(format: String): String {
    val dateTimeFormat = SimpleDateFormat(format)
    val date = Date(this * 1000)
    return dateTimeFormat.format(date)
}

fun toDayDateTimeWithFormat(format: String): String {
    return (System.currentTimeMillis() / 1000).unixDateToDateTimeString(format)
}