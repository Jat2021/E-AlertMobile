package com.example.e_alert

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_alert.main_screen.home.HomePage
import com.example.e_alert.login.LoginScreen
import com.example.e_alert.login.LoginViewModel
import com.example.e_alert.login.SignUpScreen
import com.google.firebase.auth.ktx.auth
import java.security.AccessController

enum class LoginRoutes{
    Signup,
    Signin
}

enum class HomeRoutes{
    Home,
    Signin,
    Signup
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel
){
    NavHost(
        navController = navController,
        startDestination = LoginRoutes.Signin.name
    ){
        composable(route = LoginRoutes.Signin.name){
            LoginScreen(onNavToHomePage = {
                navController.navigate((HomeRoutes.Home.name)){
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.Signin.name){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel

            ) {
                navController.navigate(LoginRoutes.Signup.name){
                    launchSingleTop = true
                    popUpTo(LoginRoutes.Signin.name){
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.Signup.name){
            SignUpScreen(onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name){
                    popUpTo(LoginRoutes.Signup.name){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel

            ) {
                navController.navigate(LoginRoutes.Signin.name)
            }
        }

        composable(route = HomeRoutes.Home.name){
            Home(onNavToSIgnIn = {
                navController.navigate(HomeRoutes.Signin.name){
                    popUpTo(LoginRoutes.Signin.name){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            )
        }
    }
}