package com.example.e_alert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

sealed class AuthScreen (var route : String) {
    object Login : AuthScreen("Login")
    object SignUp : AuthScreen("SignUp")
}

fun NavGraphBuilder.AuthNavGraph (navController : NavHostController) {
    navigation (
        startDestination = AuthScreen.Login.route,
        route = Navigation.AUTH_SCREEN
    ) {
        composable(route = AuthScreen.Login.route) {
            /*TODO: LoginPage here*/
        }
        composable(route = AuthScreen.SignUp.route) {
            /*TODO: SignUpPage here*/
        }
    }
}