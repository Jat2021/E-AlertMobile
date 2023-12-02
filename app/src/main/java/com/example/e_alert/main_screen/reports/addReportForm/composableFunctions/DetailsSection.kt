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
fun DetailsSection(addReportFormViewModel: AddReportFormViewModel? = null) {
    Column {
        var description by remember { mutableStateOf("") }

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Details"
        )

        OutlinedTextField(
            value = description,
            onValueChange = { newDescription ->
                addReportFormViewModel?.onDescriptionFieldChange(newDescription)
                description = newDescription
            },
            label = { Text(text = "Description") },
            maxLines = 4,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        PhotosToUpload(addReportFormViewModel)
    } //Column
} //DetailsSection