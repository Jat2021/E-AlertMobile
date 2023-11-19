package com.example.e_alert.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class WeatherViewModel : ViewModel() {
    private val apiKey = "6378430bc45061aaccd4a566a86c25df"
    private val latitude = 13.617
    private val longitude = 123.183

    val baseURL = "https://api.openweathermap.org/data/2.5/"

    lateinit var weatherData : Map<*,*>

//    suspend fun fetchWeatherData () : List<WeatherData> = withContext(Dispatchers.IO) {
//        val url = "${baseURL}forecast?lat=$latitude&lon=$longitude&appid=$apiKey"
//        val client = OkHttpClient()
//        val request = Request.Builder().url(url).build()
//
//        try {
//            val response = client.newCall(request).execute()
//
//            if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//            val responseData = response.body?.string()
//
//            return@withContext parseWeatherData(responseData)
//        } catch (e : Exception) {
//            throw e
//        }
//    }
//
//    private fun parseWeatherData(responseData : String?) : List<WeatherData> {
//        val gson : Gson = Gson()
//        val data = gson.fromJson(responseData, Map::class.java)
//        val forecastList = (data["list"] as List<*>)
//
//        val filteredList = forecastList.filter {
//            val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
//                .parse(it/*["dt_txt"]*/ as String)
//
//            val calendar = Calendar.getInstance()
//
//            if (dateTime != null) {
//                calendar.time = dateTime
//            }
//
//            calendar.get(Calendar.HOUR_OF_DAY) == 12 && calendar.get(Calendar.MINUTE) == 0
//        }
//
//        return filteredList.map { WeatherData.fromJson(it as Map<*, *>) }
//    }

    suspend fun fetchWeatherData () = withContext(Dispatchers.IO) {
        val url = "${baseURL}forecast?lat=$latitude&lon=$longitude&appid=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val gson : Gson = Gson()
            val data = gson.fromJson(response.body?.string(), Map::class.java)
            val forecastList = (data["list"] as List<*>)

            //val weatherDataString = data[0]

            Log.d("weatherData", "weatherData: ${forecastList[1]}")
        } catch (e : Exception) {
            throw e
        }
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