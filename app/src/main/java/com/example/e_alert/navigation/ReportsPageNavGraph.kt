package com.example.e_alert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.e_alert.main_screen.reports.AddReportForm
import com.example.e_alert.main_screen.reports.AddReportFormViewModel
import com.example.e_alert.main_screen.reports.ReportsPage
import com.example.e_alert.shared_viewModel.SharedViewModel

sealed class ReportsPageScreen (var route : String) {
    object AddReport : ReportsPageScreen("Add Report")
}

fun NavGraphBuilder.reportsPageNavGraph (
    navController: NavHostController
) {
    navigation(
        startDestination = MainScreen.ReportsPage.route,
        route = Navigation.REPORTS_PAGE
    ) {
        composable(route = MainScreen.ReportsPage.route) {
            val sharedViewModel = it.sharedViewModel<SharedViewModel>(navController = navController)
            ReportsPage(sharedViewModel = sharedViewModel, navController = navController)
        }
        composable(route = ReportsPageScreen.AddReport.route) {
            val sharedViewModel = it.sharedViewModel<SharedViewModel>(navController = navController)
            AddReportForm(addReportFormViewModel = AddReportFormViewModel())
        }
    }
}