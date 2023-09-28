package com.example.e_alert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.reportsPageNavGraph (navController : NavHostController) {
    navigation (
        startDestination = MainScreen.ReportsPage.route,
        route = Routes.ReportPageRoute.name
    ) {
        composable(route = MainScreen.RoutesPage.route) {

        }
        composable(route = ReportsPageRoute.AddReport.route) {

        }
    }
}