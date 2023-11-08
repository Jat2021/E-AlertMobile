package com.example.e_alert.main_screen.routes

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_alert.shared_viewModel.SharedViewModel

@Composable
fun RoutesPage (modifier: Modifier = Modifier) {
    val sharedViewModel : SharedViewModel = viewModel(LocalContext.current as ComponentActivity)

}