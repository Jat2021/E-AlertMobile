package com.example.e_alert.main_screen.home.homePageBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.BottomSheet
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.weather.WeatherViewModel

@Composable
fun HomePageBottomSheet(
    sharedViewModel: SharedViewModel,
    weatherViewModel: WeatherViewModel
) {
    BottomSheet () {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Column (
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                WeatherForecast(weatherViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                FloodHazardAreas(weatherViewModel, sharedViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                AccidentHazardAreas(sharedViewModel)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column (
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                RoadAccidentReports(sharedViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                FloodReports(sharedViewModel)
            }
        } //Column
    } //BottomSheet
}