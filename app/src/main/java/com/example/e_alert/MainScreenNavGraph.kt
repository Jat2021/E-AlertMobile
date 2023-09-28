package com.example.e_alert

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_alert.main_screen.home.HomePage
import com.example.e_alert.main_screen.reports.ReportsPage
import com.example.e_alert.main_screen.routes.RoutesPage

sealed class MainScreen (var route : String) {
    object HomePage : MainScreen("Home")
    object ReportsPage : MainScreen("ReportsPage")
    object RoutesPage : MainScreen("RoutesPage")
}

@Composable
fun MainScreenNavGraph (navController : NavHostController) {
    NavHost(navController = navController,
        startDestination = MainScreen.HomePage.route
    ) {
        composable(route = MainScreen.HomePage.route) {
            HomePage()
        }
        composable(route = MainScreen.ReportsPage.route) {
            ReportsPage()
        }
        composable(route = MainScreen.RoutesPage.route) {
            RoutesPage()
        }
    }

}