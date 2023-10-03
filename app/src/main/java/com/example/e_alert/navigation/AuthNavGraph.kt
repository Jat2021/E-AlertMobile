package com.example.e_alert.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.e_alert.login.LoginScreen
import com.example.e_alert.login.LoginViewModel
import com.example.e_alert.login.SignUpScreen

sealed class AuthScreen (var route : String) {
    object Login : AuthScreen("Login")
    object SignUp : AuthScreen("SignUp")
}

fun NavGraphBuilder.authNavGraph (
    navController : NavHostController,
    loginViewModel : LoginViewModel) {
    navigation (
        startDestination = AuthScreen.Login.route,
        route = Navigation.AUTH_SCREEN
    ) {
        composable(route = AuthScreen.Login.route){
            LoginScreen(onNavToHomePage = {
                navController.navigate(MainScreen.HomePage.route){
                    launchSingleTop = true
                    popUpTo(route = AuthScreen.Login.route){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel

            ) {
                navController.navigate(AuthScreen.SignUp.route){
                    launchSingleTop = true
                    popUpTo(AuthScreen.Login.route){
                        inclusive = true
                    }
                }
            }
        }

        composable(route = AuthScreen.SignUp.route){
            SignUpScreen(onNavToHomePage = {
                navController.navigate(MainScreen.HomePage.route){
                    popUpTo(AuthScreen.SignUp.route){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel

            ) {
                navController.navigate(AuthScreen.Login.route)
            }
        }
    } //navigation
} //NavGraphBuilder.authNavGraph