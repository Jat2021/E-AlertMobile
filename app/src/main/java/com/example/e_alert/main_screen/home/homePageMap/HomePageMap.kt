package com.example.e_alert.main_screen.home.homePageMap

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.e_alert.R
import com.example.e_alert.shared_viewModel.AccidentHazardAreaData
import com.example.e_alert.shared_viewModel.FloodHazardAreaData
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.weather.WeatherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomePageMap(
    listOfFloodHazardAreas : SnapshotStateList<FloodHazardAreaData>,
    listOfAccidentHazardAreas : SnapshotStateList<AccidentHazardAreaData>,
    listOfReports : SnapshotStateList<ReportData>,
    weatherViewModel : WeatherViewModel,
    getLocationUpdates : LatLng
) {

    val nagaCity = LatLng(13.621775, 123.194824)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nagaCity, 14f)
    }

    Log.d("CURRENT LOCATION", "$getLocationUpdates")

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = MapProperties(isMyLocationEnabled = true),
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
//                    EAlertMapMarker(
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
//                    EAlertMapMarker(
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
//                    EAlertMapMarker(
//                        state = MarkerState(position = hazardCoordinates),
//                        title = floodHazardArea.hazardAreaID
//                    )
//                } //listOfFloodHazardAreas.forEach
//            }
//
//            else -> { }
//        } //when statement

        val context = LocalContext.current

        listOfAccidentHazardAreas.forEach { accidentHazardArea ->
            val hazardCoordinates = LatLng(
                accidentHazardArea.coordinates.latitude,
                accidentHazardArea.coordinates.longitude
            )

            EAlertMapMarker(
                context = context,
                position = hazardCoordinates,
                title = "Road Accident Prone Area",
                snippet = "${accidentHazardArea.barangay}, ${accidentHazardArea.streetOrLandmark}",
                iconFromRes = R.drawable.road_accident_hazard_area,
            )
        }

        listOfReports.forEach { report ->
            @DrawableRes val iconFromRes : Int =
                when (report.reportType) {
                    "Flood" -> { R.drawable.flood_report }
                    else -> { R.drawable.road_accident_report }
                }

            val reportCoordinates = LatLng(
                report.reportLocation.coordinates.latitude,
                report.reportLocation.coordinates.longitude
            )

            EAlertMapMarker(
                context = context,
                position = reportCoordinates,
                title = "Report [${report.reportType}]",
                snippet = "${report.reportLocation.barangay}, ${report.reportLocation.streetOrLandmark}",
                iconFromRes = iconFromRes
            )
        }
    } //GoogleMap
} //fun HomePageMap