package com.example.e_alert.main_screen.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.weather.WeatherViewModel

@Composable
fun RoutesPage (
    sharedViewModel: SharedViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavHostController
) {
    RoutesPageMap(
        weatherViewModel = weatherViewModel,
        listOfFloodHazardAreas = sharedViewModel.floodHazardAreasListState,
        listOfAccidentHazardAreas = sharedViewModel.accidentHazardAreasListState,
        listOfReports = sharedViewModel.reportsListState,
        getLocationUpdates = sharedViewModel.getUserCurrentLocation(context = LocalContext.current)
    )
}