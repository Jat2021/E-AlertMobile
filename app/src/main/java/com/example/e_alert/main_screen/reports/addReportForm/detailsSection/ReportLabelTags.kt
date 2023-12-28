package com.example.e_alert.main_screen.reports.addReportForm.detailsSection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CarCrash
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Flood
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ReportTypeLabel (reportType : String) {
    val hazardTypes = mutableListOf(
        HazardType(
            text = "Flood",
            icon = Icons.Rounded.Flood,
            isSelected = false
        ),
        HazardType(
            text = "Road Accident",
            icon = Icons.Rounded.CarCrash,
            isSelected = false
        )
    )

    Surface(
        modifier = Modifier,
        shape = shapes.small,
        color = colorScheme.secondaryContainer
    ) {
        Row (
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = hazardTypes.find { it.text == reportType }!!.icon,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                color = colorScheme.onSecondaryContainer,
                style = typography.labelMedium,
                text = reportType.uppercase(),
            )
        }
    }
} //fun ReportTypeLabel

@Composable
fun ReportStatusTag (reportStatus : String) {
    val statusColorCode : Color = when(reportStatus) {
        "Ongoing" -> { Color(0xFF368FB8) }
        "Resolved" -> { Color(0xFF99C24D) }
        "Spam" -> { Color(0xFFD30C7B) }
        else -> { Color.Unspecified }
    }

    Surface (
        modifier = Modifier,
        shape = shapes.small,
        color = Color.Transparent
    ) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(8.dp),
                imageVector = Icons.Rounded.Circle,
                tint = statusColorCode,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(1.dp))

            Text(
                modifier = Modifier.padding(8.dp, 4.dp),
                color = statusColorCode,
                style = typography.labelMedium,
                text = reportStatus,
            )
        }
    }
}

@Composable
fun ReportVehicleTypeTag (vehicleType : String) {
    Surface (
        modifier = Modifier,
        shape = shapes.small,
        color = Color.Transparent,
        border = BorderStroke(Dp(1f), colorScheme.onSurface)
    ) {
        Text(
            modifier = Modifier.padding(8.dp, 4.dp),
            color = colorScheme.onSurface,
            style = typography.labelMedium,
            text = vehicleType,
        )
    }
}