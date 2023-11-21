package com.example.e_alert.main_screen.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.e_alert.BottomSheet
import com.example.e_alert.shared_viewModel.HazardAreaData
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.weather.WeatherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomePage () {
    val sharedViewModel : SharedViewModel = viewModel(LocalContext.current as ComponentActivity)
    val weatherViewModel : WeatherViewModel = viewModel(LocalContext.current as ComponentActivity)

    //LaunchedEffect(key1 = Unit) {
        weatherViewModel.fetchWeatherData()
    //}


    sharedViewModel.retrieveReportsFromDB()
    sharedViewModel.retrieveHazardAreasFromDB()

    Map(
        listOfHazardAreas = sharedViewModel.hazardAreasListState,
        listOfReports = sharedViewModel.reportsListState
    )
    BottomSheetHome(weatherViewModel)
}

@Composable
fun BottomSheetHome(weatherViewModel: WeatherViewModel) {
    BottomSheet () {
        val weatherForecast = weatherViewModel.get5DayForecast()

        Column(
            Modifier.fillMaxWidth()
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                weatherForecast.forEach { forecastData ->
                    Row (
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = forecastData.date)
                            AsyncImage(
                                model = forecastData.iconUrl,
                                contentScale = ContentScale.Fit,
                                contentDescription = null
                            )
                            Text(text = "${forecastData.temperature} C")
                            Text(text = forecastData.weatherDescription)
                        } //Column
                    }
                } //weatherForecast.forEach
            } //Column

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
            } //Card
        } //Box
    }
}

@Composable
fun Card(modifier: Any, content: () -> Unit) {

}

@Composable
fun Map(
    listOfHazardAreas: SnapshotStateList<HazardAreaData>,
    listOfReports: SnapshotStateList<ReportData>
) {
    val nagaCity = LatLng(13.621775, 123.194824)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nagaCity, 14f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        listOfHazardAreas.forEach { hazardArea ->
            val hazardCoordinates = LatLng(
                hazardArea.coordinates.latitude,
                hazardArea.coordinates.longitude
            )

            Marker(
                state = MarkerState(position = hazardCoordinates),
                title = hazardArea.hazardAreaID
            )
        } //listOfHazardAreas.forEach

        listOfReports.forEach { report ->
            val reportCoordinates = LatLng(
                report.reportLocation.coordinates.latitude,
                report.reportLocation.coordinates.longitude
            )

            Marker(
                state = MarkerState(position = reportCoordinates),
                title = report.reportID
            )
        }
    } //GoogleMap
}

