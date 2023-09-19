package com.example.e_alert

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ReportsPage (modifier: Modifier = Modifier) {
    //displays reports only if there are any from at least 1 users
    Surface (
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
    ) {
        Report(
            user = User(
                name = "Justin Glen Vasquez",
                profilePhoto = R.drawable.ic_launcher_foreground
            ),
            timestamp = "2 mins. ago",
            reportPhotos = listOf(
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background
            ),
            reportType = "Flood",
            reportLocation = "Sta. Cruz",
            reportDescription = "Baha na po dito sa may Sta. Cruz, Ateneo Gate"
        )
    }
}