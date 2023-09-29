package com.example.e_alert.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_alert.main_screen.home.HomePage
import com.example.e_alert.main_screen.reports.ReportsPage
import com.example.e_alert.main_screen.routes.RoutesPage

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
            HomePage()
        }
        reportsPageNavGraph(navController = navController)
        composable(route = MainScreen.RoutesPage.route) {
            RoutesPage()
        }
    }
}