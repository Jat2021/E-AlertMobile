package com.example.e_alert.main_screen.reports.addReportForm.detailsSection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CarCrash
import androidx.compose.material.icons.rounded.Flood
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel

data class HazardType (
    val text : String,
    val icon : ImageVector,
    var isSelected : Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportTypeToggleButtons (addReportFormViewModel: AddReportFormViewModel) {
    val hazardTypes = remember {
        mutableStateListOf(
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
    }

    Column {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Choose type of Report"
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            hazardTypes.forEach { type ->
                FilterChip(
                    selected = type.isSelected,
                    leadingIcon = { Icon(imageVector = type.icon, contentDescription = null) },
                    label = { Text(text = type.text) },
                    onClick = {
                        hazardTypes.replaceAll {
                            it.copy(isSelected = it.text == type.text)
                        }
                        if (hazardTypes.find { it.isSelected } != null)
                            addReportFormViewModel.onHazardTypeToggleChange(
                                hazardTypes.find { it.isSelected }!!.text
                            )
                    } //onClick
                )

                Spacer(modifier = Modifier.width(8.dp))
            } //hazardType.forEach
        } //Row
    }

}