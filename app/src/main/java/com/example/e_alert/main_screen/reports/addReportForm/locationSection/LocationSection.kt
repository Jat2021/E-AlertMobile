package com.example.e_alert.main_screen.reports.addReportForm.locationSection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun LocationSection(addReportFormViewModel: AddReportFormViewModel, currentLocation : LatLng) {
    Column {
        var streetOrLandmarkText by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }

        LaunchedEffect(addReportFormViewModel.cameraPositionState.position.target) {
            snapshotFlow { addReportFormViewModel.cameraPositionState.position.target }
                .debounce(200)
                .collect { newPosition ->
                    addReportFormViewModel.setCoordinatesAsGeopoint(newPosition)
                    addReportFormViewModel.pinnedLocationState =
                        LatLng(newPosition.latitude,newPosition.longitude)
                }
        }

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Location of Report"
        )

        Spacer(modifier = Modifier.height(8.dp))

        LocationOnMap(addReportFormViewModel, currentLocation)

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = streetOrLandmarkText,
            onValueChange = { newStreetOrLandmark ->
                addReportFormViewModel.onStreetOrLandmarkFieldChange(newStreetOrLandmark)
                streetOrLandmarkText = newStreetOrLandmark
            },
            label = { Text(text = "Street or Landmark") },
            maxLines = 4,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        Spacer(modifier = Modifier.width(16.dp))

        BarangayDropdownMenu(addReportFormViewModel)
    } //Column
} //LocationSection()