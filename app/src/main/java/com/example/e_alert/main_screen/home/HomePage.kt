package com.example.e_alert.main_screen.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Flood
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_alert.BottomSheet
import com.example.e_alert.shared_viewModel.AccidentHazardAreaData
import com.example.e_alert.shared_viewModel.FloodHazardAreaData
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.weather.WeatherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

@Composable
fun HomePage () {
    val sharedViewModel : SharedViewModel = viewModel(LocalContext.current as ComponentActivity)
    val weatherViewModel : WeatherViewModel = viewModel(LocalContext.current as ComponentActivity)

    LaunchedEffect(key1 = Unit) {
        weatherViewModel.fetchWeatherData()
    }

    sharedViewModel.retrieveReportsFromDB()
    sharedViewModel.retrieveFloodHazardAreasFromDB()
    sharedViewModel.retrieveAccidentHazardAreasFromDB()

    Map(
        weatherViewModel = weatherViewModel,
        listOfFloodHazardAreas = sharedViewModel.floodHazardAreasListState,
        listOfAccidentHazardAreas = sharedViewModel.accidentHazardAreasListState,
        listOfReports = sharedViewModel.reportsListState
    )

    BottomSheetHome(
        sharedViewModel = sharedViewModel,
        weatherViewModel = weatherViewModel
    )
}

@Composable
fun BottomSheetHome(
    sharedViewModel: SharedViewModel,
    weatherViewModel: WeatherViewModel) {
    BottomSheet () {
        val weatherForecast = weatherViewModel.get5DayForecast()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Row (
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(1f)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weatherForecast.forEach { forecastData ->
                    Box (
                        modifier = Modifier
                            .background(colorScheme.primaryContainer)
                            .padding(8.dp)
                    ) {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                style = typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                text = forecastData.dayOfTheWeek
                            )

                            Text(
                                style = typography.bodySmall,
                                fontWeight = FontWeight.Normal,
                                text = "${forecastData.month} ${forecastData.day}"
                            )

//                            val painter = rememberAsyncImagePainter(
//                                model = forecastData.iconUrl
//                            )
//
//                            Image(painter = painter, contentDescription = "Weather icon")
//
//                            if (painter.state is AsyncImagePainter.State.Loading)
//                                CircularProgressIndicator()

                            Text(
                                style = typography.headlineSmall,
                                fontWeight = FontWeight.Light,
                                text = "${forecastData.temperature} ℃"
                            )

                            Text(
                                style = typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                text = forecastData.weatherDescription.uppercase())
                        } //Column
                    }
                } //weatherForecast.forEach
            } //Column

            Spacer(modifier = Modifier.height(16.dp))

            Row (
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CrisisAlert,
                    contentDescription = null,
                    tint = colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    style = typography.titleMedium,
                    text = "Flood Hazard Areas")
            }

            Divider(modifier = Modifier.padding(8.dp))

//            var currentFloodHazard by remember {
//                mutableStateOf(weatherViewModel.checkCurrentFloodHazard())
//            }
//
//            LaunchedEffect(key1 = weatherViewModel.checkCurrentFloodHazard()) {
//                currentFloodHazard = weatherViewModel.checkCurrentFloodHazard()
//            }

//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 1.dp)
//            ) {
//                when (currentFloodHazard) {
//                    FloodHazardStatus.Low -> {
//                        sharedViewModel.floodHazardAreasListState.filter {
//                            it.riskLevel == FloodHazardStatus.High.status
//                        }.forEach { floodHazardAreaData ->
//                            FloodHazardAreaListItem(floodHazardArea = floodHazardAreaData)
//                        }
//                    }
//
//                    FloodHazardStatus.Medium -> {
//                        sharedViewModel.floodHazardAreasListState.filter {
//                            it.riskLevel == FloodHazardStatus.High.status &&
//                                it.riskLevel == FloodHazardStatus.Medium.status
//                        }.forEach { floodHazardAreaData ->
//                            FloodHazardAreaListItem(floodHazardArea = floodHazardAreaData)
//                        }
//                    }
//
//                    FloodHazardStatus.High -> {
//                        sharedViewModel.floodHazardAreasListState.forEach { floodHazardAreaData ->
//                            FloodHazardAreaListItem(floodHazardArea = floodHazardAreaData)
//                        }
//                    }
//
//                    FloodHazardStatus.VeryLowToNone -> {
//                        Box(modifier = Modifier.padding(16.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                modifier = Modifier,
//                                textAlign = TextAlign.Center,
//                                text = "No flood hazard today")
//                        }
//                    }
//
//                    else -> {
//                        Text(text = "Cannot get Flood Hazard Areas")
//                    }
//                } //when statement
//            } //Card
//
//            Spacer(modifier = Modifier.height(16.dp))

            Row (
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CrisisAlert,
                    contentDescription = null,
                    tint = colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    style = typography.titleMedium,
                    text = "Accident Hazard Areas")
            }

            Divider(modifier = Modifier.padding(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 1.dp)
            ) {
                sharedViewModel.accidentHazardAreasListState.forEach { accidentHazardAreaData ->
                    AccidentHazardAreaListItem(accidentHazardArea = accidentHazardAreaData)
                }
            }
        } //Box
    } //BottomSheet
}

@Composable
fun FloodHazardAreaListItem (floodHazardArea : FloodHazardAreaData) {
    val floodIcon = Icons.Filled.Flood
    val hazardID = floodHazardArea.hazardAreaID
    val street = floodHazardArea.street
    val barangay = floodHazardArea.barangay
    val riskLevel = floodHazardArea.riskLevel

    ListItem(
        overlineContent = { Text(hazardID) },
        headlineContent = { Text("$street, $barangay") },
        supportingContent = { Text("Risk Level: ${riskLevel.uppercase(Locale.ROOT)}") },
        leadingContent = {
            Icon(
                imageVector = floodIcon,
                contentDescription = null,
            )
        },
        trailingContent = { Text(text = "Report(s)") }
    )
} //fun FloodHazardAreaListItem

@Composable
fun AccidentHazardAreaListItem (accidentHazardArea : AccidentHazardAreaData) {
    val floodIcon = Icons.Filled.Flood
    val hazardID = accidentHazardArea.hazardAreaID
    val street = accidentHazardArea.street
    val barangay = accidentHazardArea.barangay

    ListItem(
        overlineContent = { Text(hazardID) },
        headlineContent = { Text("$street, $barangay") },
        leadingContent = {
            Icon(
                imageVector = floodIcon,
                contentDescription = null,
            )
        },
        trailingContent = { Text(text = "Report(s)") }
    )
} //fun AccidentHazardAreaListItem

@Composable
fun Map(
    listOfFloodHazardAreas: SnapshotStateList<FloodHazardAreaData>,
    listOfAccidentHazardAreas: SnapshotStateList<AccidentHazardAreaData>,
    listOfReports: SnapshotStateList<ReportData>,
    weatherViewModel: WeatherViewModel
) {
    val nagaCity = LatLng(13.621775, 123.194824)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nagaCity, 14f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
//        var currentFloodHazard by remember {
//            mutableStateOf(weatherViewModel.checkCurrentFloodHazard())
//        }

//        LaunchedEffect(key1 = weatherViewModel.checkCurrentFloodHazard()) {
//            currentFloodHazard = weatherViewModel.checkCurrentFloodHazard()
//        }
//
//        when (currentFloodHazard) {
//            FloodHazardStatus.Low -> {
//                listOfFloodHazardAreas.filter {
//                    it.riskLevel == FloodHazardStatus.High.status
//                }.forEach { floodHazardArea ->
//                    val hazardCoordinates = LatLng(
//                        floodHazardArea.coordinates.latitude,
//                        floodHazardArea.coordinates.longitude
//                    )
//
//                    Marker(
//                        state = MarkerState(position = hazardCoordinates),
//                        title = floodHazardArea.hazardAreaID
//                    )
//                } //listOfFloodHazardAreas.forEach
//            }
//
//            FloodHazardStatus.Medium -> {
//                listOfFloodHazardAreas.filter {
//                    it.riskLevel == FloodHazardStatus.High.status &&
//                            it.riskLevel == FloodHazardStatus.Medium.status
//                }.forEach { floodHazardArea ->
//                    val hazardCoordinates = LatLng(
//                        floodHazardArea.coordinates.latitude,
//                        floodHazardArea.coordinates.longitude
//                    )
//
//                    Marker(
//                        state = MarkerState(position = hazardCoordinates),
//                        title = floodHazardArea.hazardAreaID
//                    )
//                } //listOfFloodHazardAreas.forEach
//            }
//
//            FloodHazardStatus.High -> {
//                listOfFloodHazardAreas.forEach { floodHazardArea ->
//                    val hazardCoordinates = LatLng(
//                        floodHazardArea.coordinates.latitude,
//                        floodHazardArea.coordinates.longitude
//                    )
//
//                    Marker(
//                        state = MarkerState(position = hazardCoordinates),
//                        title = floodHazardArea.hazardAreaID
//                    )
//                } //listOfFloodHazardAreas.forEach
//            }
//
//            else -> { }
//        } //when statement

        listOfAccidentHazardAreas.forEach { accidentHazardArea ->
            val hazardCoordinates = LatLng(
                accidentHazardArea.coordinates.latitude,
                accidentHazardArea.coordinates.longitude
            )

            Marker(
                state = MarkerState(position = hazardCoordinates),
                title = accidentHazardArea.hazardAreaID
            )
        }

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

