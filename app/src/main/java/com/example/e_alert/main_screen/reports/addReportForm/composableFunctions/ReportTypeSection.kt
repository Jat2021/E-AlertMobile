package com.example.e_alert.main_screen.reports.addReportForm.composableFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel

@Composable
fun ReportTypeSection(addReportFormViewModel: AddReportFormViewModel? = null) {
    Column {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Report Type"
        )
        ReportTypeRadioButtons(addReportFormViewModel)
    }
} //ReportTypeSection()