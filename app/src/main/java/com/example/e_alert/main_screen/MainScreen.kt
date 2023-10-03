package com.example.e_alert.main_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_alert.BottomNavBar
import com.example.e_alert.navigation.MainScreen
import com.example.e_alert.navigation.MainScreenNavGraph
import com.example.e_alert.navigation.ReportsPageScreen
import com.example.e_alert.topbar.add_report_topbar.AddReportTopBar
import com.example.e_alert.topbar.main_screen_topbar.MainScreenTopBar

@Composable
fun MainScreen (navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        Surface (
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MainScreenNavGraph(navController = navController)
        }
    } //Scaffold
}

@Composable
fun TopBar (navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    when (currentDestination?.route) {
        ReportsPageScreen.AddReport.route -> AddReportTopBar(navController = navController)
        else -> MainScreenTopBar()
    }
}