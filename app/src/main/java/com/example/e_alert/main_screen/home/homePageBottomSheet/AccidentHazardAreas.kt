package com.example.e_alert.main_screen.home.homePageBottomSheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flood
import androidx.compose.material.icons.rounded.CrisisAlert
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.shared_viewModel.AccidentHazardAreaData
import com.example.e_alert.shared_viewModel.SharedViewModel

@Composable
fun AccidentHazardAreas (sharedViewModel: SharedViewModel) {
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
} //fun AccidentHazardAreas

@Composable
fun AccidentHazardAreaListItem (accidentHazardArea : AccidentHazardAreaData) {
    val floodIcon = Icons.Filled.Flood
    val hazardID = accidentHazardArea.hazardAreaID
    val street = accidentHazardArea.streetOrLandmark
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