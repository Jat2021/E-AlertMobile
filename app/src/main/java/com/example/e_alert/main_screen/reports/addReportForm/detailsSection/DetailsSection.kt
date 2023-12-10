package com.example.e_alert.main_screen.reports.addReportForm.detailsSection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel

@Composable
fun DetailsSection (addReportFormViewModel: AddReportFormViewModel) {
    Column {
        ReportTypeToggleButtons(addReportFormViewModel)
        DescriptionTextField(addReportFormViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        if (addReportFormViewModel.getHazardType() == "Road Accident") {
            NumberOfPeopleInvolvedDropdownMenu(addReportFormViewModel)
            Spacer(modifier = Modifier.height(16.dp))
            SelectVehicleTypes(addReportFormViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }

        UploadPhotos(addReportFormViewModel)
    }
}