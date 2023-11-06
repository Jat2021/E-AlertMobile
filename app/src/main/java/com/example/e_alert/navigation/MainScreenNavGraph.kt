package com.example.e_alert.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_alert.main_screen.home.HomePage
import com.example.e_alert.main_screen.routes.RoutesPage
import com.example.e_alert.shared_viewModel.SharedViewModel

sealed class MainScreen (var route : String) {
    object HomePage : MainScreen(route = "Home")
    object ReportsPage : MainScreen(route = "Reports")
    object RoutesPage : MainScreen(route = "Routes")
}

@Composable
fun MainScreenNavGraph(navController : NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.HomePage.route,
        route = Navigation.MAIN_SCREEN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = MainScreen.HomePage.route) {
            val sharedViewModel = it.sharedViewModel<SharedViewModel>(navController = navController)
            sharedViewModel.retrieveReportsFromDB()
            HomePage(sharedViewModel)
        }
        reportsPageNavGraph(navController = navController)
        composable(route = MainScreen.RoutesPage.route) {
            val sharedViewModel = it.sharedViewModel<SharedViewModel>(navController = navController)
            RoutesPage(sharedViewModel)
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController : NavHostController) : T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}