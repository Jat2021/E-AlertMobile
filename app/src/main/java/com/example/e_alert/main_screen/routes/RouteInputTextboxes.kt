package com.example.e_alert.main_screen.routes

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

@Composable
fun RouteInputTextboxes () {
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = origin,
            onValueChange = { newStreetOrLandmark ->
//                addReportFormViewModel.onStreetOrLandmarkFieldChange(newStreetOrLandmark)
//                streetOrLandmarkText = newStreetOrLandmark
            },
            label = { Text(text = "Street or Landmark") },
            maxLines = 4,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            suffix = {
                //TODO: Button Icon for location
            }
        )
    }
}