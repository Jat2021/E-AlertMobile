package com.example.e_alert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_alert.main_screen.reports.AddReport
import com.example.e_alert.main_screen.reports.ReportsPage
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