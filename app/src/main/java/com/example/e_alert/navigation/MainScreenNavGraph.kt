package com.example.e_alert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.e_alert.main_screen.home.HomePage
import com.example.e_alert.main_screen.reports.ReportsPage
import com.example.e_alert.main_screen.routes.RoutesPage

fun NavGraphBuilder.mainScreenNavGraph(navController : NavHostController) {
    navigation(
        startDestination = MainScreen.HomePage.route,
        route = Routes.MainScreenRoute.name
    ) {
        composable(route = MainScreen.HomePage.route) {
            HomePage()
        }
        composable(route = MainScreen.ReportsPage.route) {
            ReportsPage(navController = navController)
        }
        composable(route = MainScreen.RoutesPage.route) {
            RoutesPage()
        }
    }
}