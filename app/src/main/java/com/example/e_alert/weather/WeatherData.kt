package com.example.e_alert.weather

data class WeatherData(
    val city: City = City(
        coord = Coord(0.0,0.0),
        country = "",
        id = 0,
        name = "",
        population = 0,
        sunrise = 0,
        sunset = 0,
        timezone = 0
    ),
    val cnt: Int = 0,
    val cod: String = "",
    val list: List<WeatherList> = mutableListOf(),
    val message: Int = 0
)