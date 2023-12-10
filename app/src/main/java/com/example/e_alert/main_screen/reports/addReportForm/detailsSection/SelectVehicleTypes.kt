package com.example.e_alert.main_screen.reports.addReportForm.detailsSection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel

data class VehicleType (
    val text : String,
    var isChecked : Boolean
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectVehicleTypes (addReportFormViewModel: AddReportFormViewModel) {
    val vehicleTypes = remember {
        mutableStateListOf(
            VehicleType(
                text = "Car",
                isChecked = false
            ),
            VehicleType(
                text = "Jeepney",
                isChecked = false
            ),
            VehicleType(
                text = "Van",
                isChecked = false
            ),
            VehicleType(
                text = "Bus",
                isChecked = false
            ),
            VehicleType(
                text = "Tricycle",
                isChecked = false
            ),
            VehicleType(
                text = "Motorcycle",
                isChecked = false
            ),
            VehicleType(
                text = "Bicycle",
                isChecked = false
            ),
            VehicleType(
                text = "Truck",
                isChecked = false
            ),
            VehicleType(
                text = "Train",
                isChecked = false
            ),
            VehicleType(
                text = "Other Type",
                isChecked = false
            )
        )
    } //val vehicleTypes

    Column {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Type of vehicle(s) involved"
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
        ) {
            vehicleTypes.forEachIndexed { index, type ->
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = vehicleTypes[index].isChecked,
                        onCheckedChange = { isChecked ->
                            vehicleTypes[index] = vehicleTypes[index].copy(isChecked = isChecked)

                            val checkedVehicleTypes = emptyList<String>().toMutableList()

                            vehicleTypes.filter { vehicleType -> vehicleType.isChecked }
                                .forEach { vehicleType ->
                                    checkedVehicleTypes.add(vehicleType.text) }

                            addReportFormViewModel.onCheckTypeOfVehicle(checkedVehicleTypes)
                        }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(text = type.text)
                }
            } //vehicleTypes.foreEachIndex
        } //FlowRow
    } //Column
} //fun SelectVehicleTypes