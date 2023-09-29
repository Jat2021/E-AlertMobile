package com.example.e_alert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_alert.main_screen.MainScreen
import com.example.e_alert.ui.theme.EAlertTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EAlertTheme {
                navController = rememberNavController()
                MainScreen(navController = navController)
            } //EAlertTheme
        } //setContent
    } //onCreate
}