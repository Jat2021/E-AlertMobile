package com.example.e_alert.main_screen.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_alert.navigation.ReportsPageScreen
import com.example.e_alert.shared_viewModel.SharedViewModel

@Composable
fun ReportsPage (sharedViewModel : SharedViewModel, navController : NavHostController) {
    Scaffold (
        containerColor = colorScheme.surfaceColorAtElevation(16.dp),
        floatingActionButton = { AddReportFAB(navController) },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        if (sharedViewModel.reportsListState.isEmpty()) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.width(80.dp).height(80.dp),
                    imageVector = Icons.Rounded.CheckCircle,
                    tint = colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
                Text(
                    color = colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium,
                    style = typography.titleLarge,
                    text = "There are no Reports today"
                )
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = sharedViewModel.reportsListState) { reportData ->
                    Report(data = reportData)
                } //items
            } //LazyColumn
        } //else
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