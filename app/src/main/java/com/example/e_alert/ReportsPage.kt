package com.example.e_alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ReportsPage (modifier: Modifier = Modifier) {
    //displays reports only if there are any from at least 1 users
    Scaffold (
        containerColor = colorScheme.surfaceColorAtElevation(3.dp)
    ) { paddingValues ->
        val allDataReports = retrieveReports()

        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = allDataReports) {
                reportData -> Report(contents = reportData)
            } //items
        } //LazyColumn
    } //Scaffold
} //ReportsPage