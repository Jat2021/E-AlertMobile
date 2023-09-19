package com.example.e_alert

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ReportsPage (modifier: Modifier = Modifier) {
    //displays reports only if there are any from at least 1 users
    Surface (
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
    ) {
        Report(
            modifier = modifier,
            user = User(
                name = "Justin Glen Vasquez",
                profilePhoto = R.drawable.ic_launcher_foreground
            ),
            timestamp = "2 mins. ago",
            reportDescription = "Baha na po dito sa may Sta. Cruz, Ateneo Gate",
            reportPhotos = R.drawable.ic_launcher_background,
            reportType = "Flood",
            reportLocation = "Sta. Cruz"
        )
    }
}