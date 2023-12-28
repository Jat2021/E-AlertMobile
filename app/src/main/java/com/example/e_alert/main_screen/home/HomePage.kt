package com.example.e_alert.main_screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.e_alert.main_screen.home.homePageBottomSheet.HomePageBottomSheet
import com.example.e_alert.main_screen.home.homePageMap.HomePageMap
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.weather.WeatherViewModel

@Composable
fun HomePage (
    sharedViewModel: SharedViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavHostController
) {
    sharedViewModel.retrieveReportsFromDB()
    sharedViewModel.retrieveFloodHazardAreasFromDB()
    sharedViewModel.retrieveAccidentHazardAreasFromDB()

    HomePageMap(
        weatherViewModel = weatherViewModel,
        listOfFloodHazardAreas = sharedViewModel.floodHazardAreasListState,
        listOfAccidentHazardAreas = sharedViewModel.accidentHazardAreasListState,
        listOfReports = sharedViewModel.reportsListState,
        getLocationUpdates = sharedViewModel.getUserCurrentLocation(context = LocalContext.current)
    )

    HomePageBottomSheet(
        sharedViewModel = sharedViewModel,
        weatherViewModel = weatherViewModel
    )
} //HomePage

