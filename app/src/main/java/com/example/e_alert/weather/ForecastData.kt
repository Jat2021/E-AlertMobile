package com.example.e_alert.weather

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale

data class ForecastData(
    val date : String,
    val temperature : Int,
    val weatherDescription : String,
    val iconUrl : String
) {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun fromJson(weatherList : WeatherList) : ForecastData {
            val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .parse(weatherList.dt_txt)
            val formattedDate = dateTime?.let { SimpleDateFormat("yyyy-MM-dd").format(it) }
            val temperature = (weatherList.main.temp - 273.15).toInt()
            val weatherDescription = (weatherList.weather as Map<*,*>)
            val iconCode = weatherDescription["icon"] as String
            val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"

            return ForecastData(
                date = formattedDate!!,
                temperature = temperature,
                weatherDescription = weatherDescription["description"] as String,
                iconUrl = iconUrl
            )
        }
    }
}