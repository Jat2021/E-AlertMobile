package com.example.e_alert.main_screen.home.homePageBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.e_alert.weather.WeatherViewModel

@Composable
fun WeatherForecast (weatherViewModel: WeatherViewModel) {
    Row (
        modifier = Modifier
            .fillMaxWidth(1f)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (weatherViewModel.fiveDayForecastList.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Cannot display 5-day Weather Forecast")
            }
        } else {
            weatherViewModel.fiveDayForecastList.forEach { forecastData ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            text = forecastData.dayOfTheWeek
                        )

                        Text(
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Normal,
                            text = "${forecastData.month} ${forecastData.day}"
                        )

//                            val painter = rememberAsyncImagePainter(
//                                model = forecastData.iconUrl
//                            )
//
//                            Image(painter = painter, contentDescription = "Weather icon")
//
//                            if (painter.state is AsyncImagePainter.State.Loading)
//                                CircularProgressIndicator()

                        Text(
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Light,
                            text = "${forecastData.temperature} ℃"
                        )

                        Text(
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            text = forecastData.weatherDescription.uppercase()
                        )
                    } //Column
                }
            } //weatherForecast.forEach
        } //else
    } //Column
}