package com.example.e_alert.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_alert.login.LoginViewModel
import com.example.e_alert.main_screen.MainScreen

object Navigation {
    const val ROOT = "Root"
    const val AUTH_SCREEN = "Auth Screen"
    const val MAIN_SCREEN = "Main Screen"
    const val HOME_PAGE = "Home Page"
    const val REPORTS_PAGE = "Reports Page"
    const val ROUTES_PAGE = "Routes Page"
}

@Composable
fun RootNavGraph (navController : NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Navigation.AUTH_SCREEN,
        route = Navigation.ROOT
    ) {
        authNavGraph(navController = navController, loginViewModel = LoginViewModel())
        composable(route = MainScreen.HomePage.route) {
            MainScreen()
        }
    }

}