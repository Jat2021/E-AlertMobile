package com.example.e_alert.main_screen.reports.addReportForm.locationSection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun LocationSection(addReportFormViewModel: AddReportFormViewModel, currentLocation : LatLng) {
    Column {
        var streetOrLandmarkText by remember { mutableStateOf("") }

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Location of Report"
        )

        Spacer(modifier = Modifier.height(8.dp))

        LocationOnMap(addReportFormViewModel, currentLocation)

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

        BarangayDropdownMenu(addReportFormViewModel)
    } //Column
} //LocationSection()