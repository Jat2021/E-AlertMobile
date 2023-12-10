package com.example.e_alert.main_screen.home.homePageBottomSheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flood
import androidx.compose.material.icons.rounded.CrisisAlert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.shared_viewModel.FloodHazardAreaData
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.weather.WeatherViewModel
import java.util.Locale

@Composable
fun FloodHazardAreas (
    weatherViewModel: WeatherViewModel,
    sharedViewModel: SharedViewModel
) {
    Row (
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.CrisisAlert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Flood Hazard Areas"
        )
    }

    Divider(modifier = Modifier.padding(8.dp))

//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 1.dp)
//            ) {
//                when (weatherViewModel.floodHazardStatus) {
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
} //fun FloodHazardAreas

@Composable
fun FloodHazardAreaListItem (floodHazardArea : FloodHazardAreaData) {
    val floodIcon = Icons.Filled.Flood
    val hazardID = floodHazardArea.hazardAreaID
    val street = floodHazardArea.streetOrLandmark
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