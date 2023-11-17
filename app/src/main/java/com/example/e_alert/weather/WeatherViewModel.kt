package com.example.e_alert.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weatherData : WeatherData) : WeatherState()
    data class Error(val message : String) : WeatherState()
}

class WeatherViewModel : ViewModel() {
    private val apiKey = "6378430bc45061aaccd4a566a86c25df"
    private val latitude = 13.617
    private val longitude = 123.183

    val baseURL = "https://api.openweathermap.org/data/2.5/"

    val weatherData = mutableListOf<WeatherData>()



    fun fetchWeatherData () {
        viewModelScope.launch {
            val weatherService : WeatherAPI = retrofit.create(WeatherAPI::class.java)

            val response = weatherService.getWeatherData(
                latitude = latitude,
                longitude = longitude,
                apiKey = apiKey
            )


        }
    }
}