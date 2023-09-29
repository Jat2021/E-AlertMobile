package com.example.e_alert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_alert.main_screen.MainScreen
import com.example.e_alert.navigation.RootNavGraph
import com.example.e_alert.ui.theme.EAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EAlertTheme {
                RootNavGraph(navController = rememberNavController())
            } //EAlertTheme
        } //setContent
    } //onCreate
}