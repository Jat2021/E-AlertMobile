package com.example.e_alert.weather

import android.util.Log
import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class WeatherViewModel : ViewModel() {
    private val apiKey = "6378430bc45061aaccd4a566a86c25df"
    private val latitude = 13.617
    private val longitude = 123.183

    val baseURL = "https://api.openweathermap.org/data/2.5/"

    private var _weatherData : WeatherData = WeatherData()
    var weatherData by mutableStateOf(_weatherData)

    suspend fun fetchWeatherData () = withContext(Dispatchers.IO) {
        val url = "${baseURL}forecast?lat=$latitude&lon=$longitude&appid=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val gson : Gson = Gson()
            _weatherData = gson.fromJson(response.body?.string(), WeatherData::class.java)

            Log.d("WeatherData", "WeatherData data: ${_weatherData.list[0].rain.`3h`}")
        } catch (e : Exception) {
            throw e
        }
    }

    //This returns a list of 5-day forecast
    fun get5DayForecast () : List<ForecastData> {

        return emptyList()
    }

    fun getRainForecast () {

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