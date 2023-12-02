package com.example.e_alert.main_screen.reports.addReportForm.composableFunctions

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
fun BarangayDropdownMenu(addReportFormViewModel : AddReportFormViewModel? = null) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var barangay by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded ,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            value = barangay,
            onValueChange = { addReportFormViewModel?.onSelectBarangay(barangay) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = isExpanded)
            },
            label = { Text(text = "Barangay") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            addReportFormViewModel!!.listOfBarangayState.forEach { barangayItem ->
                DropdownMenuItem(
                    text = { Text(text = barangayItem) },
                    onClick = {
                        barangay = barangayItem
                        addReportFormViewModel.onSelectBarangay(barangay)
                        isExpanded = false
                    }
                )
            }
        } //ExposedDropdownMenu
    } //ExposedDropdownMenuBox
}