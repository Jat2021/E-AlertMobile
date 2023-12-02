package com.example.e_alert.weather

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed class FloodHazardStatus (val status : String) {
    object VeryLowToNone : FloodHazardStatus(status = "Very Low to None")
    object Low : FloodHazardStatus(status = "Low")
    object Medium : FloodHazardStatus(status = "Medium")
    object High : FloodHazardStatus(status = "High")
}

class WeatherViewModel : ViewModel() {
    private val apiKey = "6378430bc45061aaccd4a566a86c25df"
    private val latitude = 13.617
    private val longitude = 123.183

    private val baseURL = "https://api.openweathermap.org/data/2.5/"

    var fiveDayForecastList = mutableStateListOf<ForecastData>()

    private var _floodHazardStatus : FloodHazardStatus? = null
    var floodHazardStatus by mutableStateOf(_floodHazardStatus)

    init {
        getFiveDayForecast().start()
        //checkCurrentFloodHazard().start()
    }

    //This returns a list of 5-day forecast
    private fun getFiveDayForecast () = viewModelScope.launch(Dispatchers.IO) {
        val url = "${baseURL}forecast?lat=$latitude&lon=$longitude&appid=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val weatherData : WeatherData

        try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val gson = Gson()
            val data : WeatherData = gson.fromJson(response.body?.string(), WeatherData::class.java)

            weatherData = data
        } catch (e : Exception) {
            throw e
        }

        Log.d("WeatherData @giveFiveDayForecast()","weatherData.list[0]: ${weatherData.list[0]}")

        val filteredWeatherList = weatherData.list.filter { weatherList ->
            LocalDateTime.parse(weatherList.dt_txt,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).hour == 12
        }

        filteredWeatherList.forEach {
            fiveDayForecastList.add(ForecastData().fromJson(it))
        }

        Log.d("FiveDayForecast @giveFiveDayForecast()","fiveDayForecastList: ${fiveDayForecastList[0]}")
    }

    private fun checkCurrentFloodHazard() = viewModelScope.launch(Dispatchers.IO) {
        val url = "${baseURL}forecast?lat=$latitude&lon=$longitude&appid=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val weatherData : WeatherData

        try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val gson = Gson()
            val data : WeatherData = gson.fromJson(response.body?.string(), WeatherData::class.java)

            weatherData = data
        } catch (e : Exception) {
            throw e
        }

        Log.d("Rain.3h @checkCurrentFloodHazard()",
            "weatherData.list[0].rain.`3h`: ${weatherData.list[0].rain.`3h`}")

        val rain1 : Double? = weatherData.list[0].rain.`3h`
        val rain2 : Double? = weatherData.list[1].rain.`3h`
        val rain3 : Double? = weatherData.list[2].rain.`3h`


        if ((rain1 != null) && (rain2 != null) && (rain3 != null)) {
            if ((rain1 in 6.5..15.0) && (rain2 in 6.5..15.0) && (rain3 in 6.5..15.0)) {
                floodHazardStatus = FloodHazardStatus.Low
            }
            if ((rain1 > 15.0 && rain1 <= 30.0) && (rain2 > 15.0 && rain2 <= 30.0)
                && (rain3 > 15.0 && rain3 <= 30.0)) {
                floodHazardStatus = FloodHazardStatus.Medium
            }

            else if (rain1 > 30.0 && rain2 > 30.0 && rain3 > 30.0) {
                floodHazardStatus = FloodHazardStatus.High
            }

            else if ((rain1 < 6.5) && (rain2 < 6.5) && (rain3 < 6.5))
                floodHazardStatus = FloodHazardStatus.VeryLowToNone
        }
    } //fun checkCurrentFloodHazard()
}