package com.example.e_alert.navigation

import androidx.activity.ComponentActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_alert.main_screen.home.HomePage
import com.example.e_alert.main_screen.routes.RoutesPage
import com.example.e_alert.main_screen.profile.ProfilePage
import com.example.e_alert.main_screen.profile.ProfilePageViewModel
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.weather.WeatherViewModel

sealed class MainScreen (var route : String) {
    object HomePage : MainScreen(route = "Home")
    object ReportsPage : MainScreen(route = "Reports")
    object RoutesPage : MainScreen(route = "Routes")
    object ProfilePage : MainScreen(route = "Profile")
}


@Composable
fun MainScreenNavGraph(navController : NavHostController) {
    val sharedViewModel : SharedViewModel = viewModel(LocalContext.current as ComponentActivity)
    val weatherViewModel : WeatherViewModel = viewModel(LocalContext.current as ComponentActivity)
    val profilePageViewModel : ProfilePageViewModel = viewModel(LocalContext.current as ComponentActivity)

    NavHost(
        navController = navController,
        startDestination = MainScreen.HomePage.route,
        route = Navigation.MAIN_SCREEN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = MainScreen.HomePage.route) {
            HomePage(
                navController = navController,
                sharedViewModel = sharedViewModel,
                weatherViewModel = weatherViewModel
            )
        }
        reportsPageNavGraph(navController = navController)
        composable(route = MainScreen.RoutesPage.route) {
            RoutesPage(
                navController = navController,
                sharedViewModel = sharedViewModel,
                weatherViewModel = weatherViewModel)
        }
        composable(route = MainScreen.ProfilePage.route) {
            ProfilePage(
                profilePageViewModel = profilePageViewModel,
                onNavigateBackToMain = { navController.popBackStack() }
            )
        }
    }
}