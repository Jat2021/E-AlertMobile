package com.example.e_alert.weather

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherViewModel : ViewModel() {
    private val apiKey = "6378430bc45061aaccd4a566a86c25df"
    private val latitude = 13.617
    private val longitude = 123.183

    val baseURL = "https://api.openweathermap.org/data/2.5/"

    private var weatherData : WeatherData = WeatherData()

    suspend fun fetchWeatherData () = withContext(Dispatchers.IO) {
        val url = "${baseURL}forecast?lat=$latitude&lon=$longitude&appid=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val gson : Gson = Gson()
            weatherData = gson.fromJson(response.body?.string(), WeatherData::class.java)

            //Log.d("WeatherData", "WeatherData data: ${filteredWeatherList[0]}")
        } catch (e : Exception) {
            throw e
        }
    }

    //This returns a list of 5-day forecast
    fun get5DayForecast () : List<ForecastData> {
        val forecastDataList : MutableList<ForecastData> = emptyList<ForecastData>().toMutableList()

        val filteredWeatherList = weatherData.list.filter { weatherList ->
            LocalDateTime.parse(weatherList.dt_txt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .hour == 12
        }

        for (i in 0..4) {
            forecastDataList.add(
                ForecastData.fromJson(filteredWeatherList[i])
            )
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

//    fun checkHazardRisk() : String {
//        var hazardRisk : String = ""
//
//        if (weatherData["list"][0]["rain"]["3h"] >= 6.5 && weatherData["list"][0]["rain"]["3h"] <= 15.0) {
//            if (weatherData["list"][1]["rain"]["3h"] >= 6.5 && weatherData["list"][1]["rain"]["3h"] <= 6.5) {
//                if (weatherData["list"][2]["rain"]["3h"] >= 6.5 && weatherData["list"][2]["rain"]["3h"] <= 6.5) {
//                    hazardRisk = "low"
//                }
//            }
//        }
//
//        else if (weatherData["list"][0]["rain"]["3h"] >= 15.0 && weatherData["list"][0]["rain"]["3h"] >= 30.0) {
//            if (weatherData["list"][1]["rain"]["3h"] >= 15.0 && weatherData["list"][1]["rain"]["3h"] >= 30.0) {
//                if (weatherData["list"][2]["rain"]["3h"] >= 15.0 && weatherData["list"][2]["rain"]["3h"] >= 30.0) {
//                    hazardRisk = "Medium"
//                }
//            }
//        }
//        return hazardRisk
//    } //fun checkHazardRisk()
}