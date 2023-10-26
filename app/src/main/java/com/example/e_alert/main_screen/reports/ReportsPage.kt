package com.example.e_alert.main_screen.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.e_alert.navigation.ReportsPageScreen
import kotlinx.coroutines.launch

@Composable
fun ReportsPage (navController: NavHostController) {
    val reportsPageViewModel = ReportsPageViewModel()

    Scaffold (
        containerColor = colorScheme.surfaceColorAtElevation(3.dp),
        floatingActionButton = { AddReportFAB(navController) },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        reportsPageViewModel.getAllReports()

        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = reportsPageViewModel.allReportsListState) { reportData ->
                Report(data = reportData)
            } //items
        } //LazyColumn
    } //Scaffold
} //ReportsPage

@Composable
fun AddReportFAB(navController: NavHostController) {
    ExtendedFloatingActionButton(
        containerColor = colorScheme.tertiaryContainer,
        onClick = { navController.navigate(ReportsPageScreen.AddReport.route) }) {
        Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
        Text(text = "Add Report")
    }
}