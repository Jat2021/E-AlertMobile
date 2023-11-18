package com.example.e_alert.weather

import java.text.SimpleDateFormat
import java.util.Locale

data class WeatherData(
    val date : String,
    val temperature : Int,
    val weatherDescription : String,
    val iconUrl : String
) {
    companion object {
        fun fromJson(json : Map<*, *>) : WeatherData {
            val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .parse(json["dt_txt"].toString())
            val formattedDate = dateTime?.let { SimpleDateFormat("yyyy-MM-dd").format(it) }
            val temperature = ((json["main"] as Map<*, *> )["temp"] as Double - 273.15).toInt()
            val weatherDescription = (json["weather"] as List<*>)[0] as Map<*, *>
            val iconCode = weatherDescription["icon"] as String
            val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"

            return WeatherData(
                date = formattedDate!!,
                temperature = temperature,
                weatherDescription = weatherDescription["description"] as String,
                iconUrl = iconUrl
            )
        }
    }
}