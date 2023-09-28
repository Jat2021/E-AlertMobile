package com.example.e_alert.main_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.e_alert.BottomNavBar
import com.example.e_alert.main_screen.reports.AddReportForm
import com.example.e_alert.navigation.NavGraph
import com.example.e_alert.topbar.TopBar

@Composable
fun MainScreen (navController : NavHostController) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        Surface (
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavGraph(navController = navController)
        }
    } //Scaffold
}