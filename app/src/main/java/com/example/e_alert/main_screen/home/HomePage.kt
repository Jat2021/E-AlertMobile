package com.example.e_alert.main_screen.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_alert.BottomSheet
import com.example.e_alert.shared_viewModel.HazardAreaData
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomePage () {
    val sharedViewModel : SharedViewModel = viewModel(LocalContext.current as ComponentActivity)
    sharedViewModel.retrieveReportsFromDB()
    sharedViewModel.retrieveHazardAreasFromDB()

    Map(
        listOfHazardAreas = sharedViewModel.hazardAreasListState,
        listOfReports = sharedViewModel.reportsListState
    )
    BottomSheetHome()
}

@Composable
fun BottomSheetHome () {
    BottomSheet () {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
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
            }
        }
    }
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

    val listOfHazardAreaPins =

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
    }
}

