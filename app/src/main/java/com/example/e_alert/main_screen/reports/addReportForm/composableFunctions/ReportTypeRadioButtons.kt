package com.example.e_alert.main_screen.reports.addReportForm.composableFunctions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

@Composable
fun ReportTypeRadioButtons(addReportFormViewModel: AddReportFormViewModel? = null) {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = false,
                text = "Flood"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Road Accident"
            )
        )
    } //remember

    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        radioButtons.forEach { info ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        addReportFormViewModel?.onHazardTypeToggleChange(info.text)
                    }
            ) {
                RadioButton(
                    selected = info.isChecked,
                    onClick = {
                        radioButtons.replaceAll{
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        addReportFormViewModel?.onHazardTypeToggleChange(info.text)
                    }
                )
                Text(text = info.text)
            }
        }
    } //Row
} //ReportTypeRadioButtons()