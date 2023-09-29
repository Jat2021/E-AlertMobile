package com.example.e_alert.topbar.add_report_topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.e_alert.navigation.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReportTopBar (navController : NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors
            (titleContentColor = MaterialTheme.colorScheme.primary),
        title = { Text(text = "Add Report") },
        actions = {
            IconButton(onClick = { navController.navigate(MainScreen.ReportsPage.route) }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        }
    )
}