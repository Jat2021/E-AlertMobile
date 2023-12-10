package com.example.e_alert.main_screen.reports.addReportForm.detailsSection

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberOfPeopleInvolvedDropdownMenu (addReportFormViewModel : AddReportFormViewModel) {
    val unableToCount = "Unable to count/more than 10"

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var count by remember {
        mutableStateOf(unableToCount)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded ,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            value = count,
            onValueChange = { addReportFormViewModel.onSelectNumberOfPersonsInvolved(count) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = isExpanded)
            },
            label = { Text(text = "Number of people involved") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = unableToCount) },
                onClick = {
                    count = unableToCount
                    addReportFormViewModel.onSelectNumberOfPersonsInvolved(count)
                    isExpanded = false
                }
            )
            for (number in 1..10) {
                DropdownMenuItem(
                    text = { Text(text = number.toString()) },
                    onClick = {
                        count = "$number person(s) involved"
                        addReportFormViewModel.onSelectNumberOfPersonsInvolved(count)
                        isExpanded = false
                    }
                )
            }
        } //ExposedDropdownMenu
    } //ExposedDropdownMenuBox
}