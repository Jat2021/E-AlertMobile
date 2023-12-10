package com.example.e_alert.main_screen.home.homePageBottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.SharedViewModel

@Composable
fun RoadAccidentReports (sharedViewModel: SharedViewModel) {
    Row (
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Road Accident Reports")
    }

    Divider(modifier = Modifier.padding(8.dp))


    if (sharedViewModel.reportsListState.none { it.reportType == "Road Accident" }) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = "No Road Accident reports today"
            )
        }
    }

    else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
        ) {
            sharedViewModel.reportsListState.filter { it.reportType == "Road Accident" }
                .forEach { roadAccidentReport ->
                    RoadAccidentReportListItem(roadAccidentReport)
                }
        }
    } //else
} //fun RoadAccidentReports

@Composable
fun RoadAccidentReportListItem (report : ReportData) {
    val roadAccidentIcon = Icons.Filled.CarCrash
    val reportID = report.reportID
    val streetOrLandmark = report.reportLocation.streetOrLandmark
    val barangay = report.reportLocation.barangay

    ListItem(
        overlineContent = { Text(reportID) },
        headlineContent = { Text("$streetOrLandmark, $barangay") },
        leadingContent = {
            Icon(
                imageVector = roadAccidentIcon,
                contentDescription = null,
            )
        }
    )
} //fun AccidentHazardAreaListItem