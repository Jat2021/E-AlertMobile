package com.example.e_alert.main_screen.home.homePageMap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Flood
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.e_alert.weather.WeatherViewModel

sealed class FloodAlertLevel(
    val title : String = "",
    val text : String = "",
    val icon : ImageVector = Icons.Rounded.Flood,
) {
    object YellowWarning : FloodAlertLevel()
}

@Composable
fun FloodHazardLevelAlert (weatherViewModel: WeatherViewModel) {
    Column (
        modifier = Modifier.padding(8.dp)
    ) {
        Row (modifier = Modifier.fillMaxSize(2f)) {
            //Icon(imageVector = , contentDescription = )
        }
        Row (modifier = Modifier.fillMaxSize(1f)) {
            //
        }
    }
}