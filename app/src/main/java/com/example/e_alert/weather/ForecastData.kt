package com.example.e_alert.weather

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ForecastData(
    val date : String,
    val temperature : Int,
    val weatherDescription : String,
    val iconUrl : String
) {
    companion object {
        fun fromJson(weatherList : WeatherList) : ForecastData {
            val dateTime = LocalDateTime.parse(weatherList.dt_txt,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val temperature = (weatherList.main.temp - 273.15).toInt()
            val weatherDescription = weatherList.weather[0]
            val iconCode = weatherDescription.icon
            val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"

            return ForecastData(
                date = formattedDate!!,
                temperature = temperature,
                weatherDescription = weatherDescription.description,
                iconUrl = iconUrl
            )
        }
    }
}