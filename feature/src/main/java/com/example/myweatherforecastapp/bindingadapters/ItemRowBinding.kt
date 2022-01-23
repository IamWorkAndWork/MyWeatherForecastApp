package com.example.myweatherforecastapp.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.core.ext.unixDateToDateTimeString
import com.example.core.model.DATE_TIME_FORMAT_DD_MM_YYYY_HH_MM
import com.example.core.model.DATE_TIME_FORMAT_HH_MM
import com.example.core.model.DATE_TIME_FORMAT_d_MMM_yyyy
import com.example.domain.model.CityEntity
import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.ext.UnitTypeToTemperatureIdText
import com.example.myweatherforecastapp.model.UnitType

class ItemRowBinding {

    companion object {

        @BindingAdapter("setTextTime")
        @JvmStatic
        fun setTextTime(textView: TextView, datetime: Long) {
            textView.text = "${
                datetime.unixDateToDateTimeString(
                    DATE_TIME_FORMAT_HH_MM
                )
            }"
        }

        @BindingAdapter("setTextDate")
        @JvmStatic
        fun setTextDate(textView: TextView, datetime: Long) {
            textView.text = "${
                datetime.unixDateToDateTimeString(
                    DATE_TIME_FORMAT_d_MMM_yyyy
                )
            }"
        }

        @BindingAdapter("setCountryName")
        @JvmStatic
        fun setCountryName(textView: TextView, cityEntity: CityEntity) {
            textView.text = "${cityEntity.country} (${cityEntity.iso3})"
        }

        @BindingAdapter("setTemperatureUnitText")
        @JvmStatic
        fun setTemperatureUnitText(textView: TextView, unit: String) {
            val unitStringId =  when (unit) {
                UnitType.Celsius.unit -> {
                    R.string.label_celsius
                }
                else -> {
                    R.string.label_fahrenheit
                }
            }
            textView.text = textView.context.getString(unitStringId)
        }

        @BindingAdapter("setImageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String) {
            println("image url = $url")
            imageView.load(url) {
                crossfade(600)
                error(R.drawable.ic_baseline_insert_photo)
            }
        }

    }

}