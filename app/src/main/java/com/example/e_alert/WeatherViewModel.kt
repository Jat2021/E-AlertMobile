package com.example.e_alert

class WeatherViewModel {
    /*TODO: From Weather API*/

    sealed class Alert (val title : String) {
        object Safe : Alert("Safe")
        object Warning : Alert("Warning")
        object Danger : Alert("Danger")
    }

    fun checkWeatherUpdates () {

    }
}