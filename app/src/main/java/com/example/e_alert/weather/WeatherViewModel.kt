package com.example.e_alert.weather

import android.util.Log
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

    private var weatherData : WeatherData = WeatherData()
    private var filteredWeatherList : MutableList<WeatherList> = emptyList<WeatherList>().toMutableList()


    fun fetchWeatherData () = viewModelScope.launch(Dispatchers.IO) {
        val url = "${baseURL}forecast?lat=$latitude&lon=$longitude&appid=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val gson = Gson()
            val data = gson.fromJson(response.body?.string(), WeatherData::class.java)

            weatherData = data

            filteredWeatherList = weatherData.list.filter { weatherList ->
                LocalDateTime.parse(weatherList.dt_txt,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).hour == 12
            }.toMutableList()

            //Log.d("WeatherData", "WeatherData data: ${weatherData.list[0]}")
        } catch (e : Exception) {
            throw e
        }
    }

    //This returns a list of 5-day forecast
    fun get5DayForecast () : List<ForecastData> {
        val forecastDataList : MutableList<ForecastData> = mutableListOf()



        filteredWeatherList = weatherData.list.filter { weatherList ->
            LocalDateTime.parse(weatherList.dt_txt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .hour == 12
        }.toMutableList()

        filteredWeatherList.forEach {
            forecastDataList.add(ForecastData.fromJson(it))
        }

        return forecastDataList
    }

    fun getRainForecast () : List<String> {
        val rainVolume : MutableList<String> = emptyList<String>().toMutableList()

        for (i in 0..2) {
            val time = LocalDateTime.now()

            rainVolume.add(
                "Rain Volume [${time.hour}:${time.minute}] ${weatherData.list[i].rain.`3h`} mm."
            )
        }
        return rainVolume
    }

    fun checkCurrentFloodHazard() : FloodHazardStatus? {
        var hazardStatus : FloodHazardStatus? = null

        Log.d("weatherData.list[0] @ checkCurrentFloodHazard",
            "weatherData.list[0]: ${weatherData.list[0]}")

        val rain1 = weatherData.list[0].rain.`3h`
        val rain2 = weatherData.list[1].rain.`3h`
        val rain3 = weatherData.list[2].rain.`3h`

        if ((rain1 in 6.5..15.0) && (rain2 in 6.5..15.0) && (rain3 in 6.5..15.0)) {
            hazardStatus = FloodHazardStatus.Low
        }

        else if ((rain1 > 15.0 && rain1 <= 30.0) && (rain2 > 15.0 && rain2 <= 30.0)
            && (rain3 > 15.0 && rain3 <= 30.0)) {
            hazardStatus = FloodHazardStatus.Medium
        }

        else if (rain1 > 30.0 && rain2 > 30.0 && rain3 > 30.0) {
            hazardStatus = FloodHazardStatus.High
        }

        else if ((rain1 < 6.5) && (rain2 < 6.5) && (rain3 < 6.5))
            hazardStatus = FloodHazardStatus.VeryLowToNone

        return hazardStatus
    } //fun checkCurrentFloodHazard()
}