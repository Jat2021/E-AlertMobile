package com.example.e_alert.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

sealed class Routes (var name : String) {
    object RootRoute : Routes("Root")
    object AuthRoute : Routes("Auth")
    object MainScreenRoute : Routes("Main Screen")
    object HomePageRoute : Routes("Home Page")
    object ReportPageRoute : Routes("Report Page")
}

sealed class MainScreen (var route : String) {
    object HomePage : MainScreen("Home")
    object ReportsPage : MainScreen("Reports")
    object RoutesPage : MainScreen("Routes")
}

sealed class HomePageRoute (var route : String) {

}

sealed class ReportsPageRoute (var route : String) {
    object AddReport : ReportsPageRoute("Add Report")
}

sealed class AuthScreen (var route : String) {
    object LoginPage : AuthScreen("Login")
    object SignUp : AuthScreen("SignUp")
}

@Composable
fun NavGraph (navController : NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.HomePage.route,
        route = Routes.RootRoute.name
    ) {
        mainScreenNavGraph(navController = navController)
        reportsPageNavGraph(navController = navController)
    }

}