package com.example.e_alert.topbar.main_screen_topbar

import android.annotation.SuppressLint
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar() {
    TopAppBar(
        title = { Text(
            //fontFamily = , TODO: Use RobotoFlex font
            text = "E-Alert") },
        actions = { TopBarAccountMenu() } //actions
    )
}