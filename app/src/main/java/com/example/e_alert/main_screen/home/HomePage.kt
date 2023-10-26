package com.example.e_alert.main_screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_alert.BottomSheet
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomePage () {
    val nagaCity = LatLng(13.621775, 123.194824)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nagaCity, 14f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = nagaCity),
            title = "Naga City, Camarines Sur",
            snippet = "City of Naga"
        )
    }

    BottomSheetHome()
}

@Composable
fun BottomSheetHome () {
    BottomSheet () {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
                ListItem(
                    headlineContent = { Text("Three line list item") },
                    overlineContent = { Text("OVERLINE") },
                    supportingContent = { Text("Secondary text") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = { Text("meta") }
                )
            }
        }
    }
}

