package com.example.e_alert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.e_alert.reports.ReportsPage
import com.example.e_alert.ui.theme.EAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EAlertTheme {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavBar() }
                ) { paddingValues ->
                    Surface (
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        //HomePage()
                        ReportsPage()
                    }
                }
            } //EAlertTheme
        } //setContent
    } //onCreate
}