package com.example.e_alert.main_screen.reports.addReportForm.composableFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel

@Composable
fun LocationSection(addReportFormViewModel: AddReportFormViewModel? = null) {
    Column {
        var streetText by remember { mutableStateOf("") }

        LocationOnMap(addReportFormViewModel)

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Location"
        )

        OutlinedTextField(
            value = streetText,
            onValueChange = { newStreet ->
                addReportFormViewModel?.onStreetFieldChange(newStreet)
                streetText = newStreet
            },
            label = { Text(text = "Street") },
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        BarangayDropdownMenu(addReportFormViewModel)
    } //Column
} //LocationSection()