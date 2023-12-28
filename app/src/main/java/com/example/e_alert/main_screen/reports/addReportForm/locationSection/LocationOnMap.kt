package com.example.e_alert.main_screen.reports.addReportForm.locationSection

import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import java.util.Locale

@OptIn(FlowPreview::class, ExperimentalComposeUiApi::class)
@Composable
fun LocationOnMap (addReportFormViewModel: AddReportFormViewModel, currentLocation : LatLng) {
    val isCurrentLocationButtonPressed by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(addReportFormViewModel.cameraPositionState.position.target) {
        snapshotFlow { addReportFormViewModel.cameraPositionState.position.target }
            .debounce(200)
            .collect { newPosition ->
                addReportFormViewModel.setCoordinatesAsGeopoint(newPosition)
                addReportFormViewModel.pinnedLocationState =
                    LatLng(newPosition.latitude,newPosition.longitude)
            }
    }

    Column (
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            GoogleMap(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxSize()
                    .pointerInteropFilter(onTouchEvent = {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                addReportFormViewModel.isScrollEnabled = false
                                false
                            }

                            else -> {
                                addReportFormViewModel.isScrollEnabled = true
                                true
                            }
                        }
                    }),
                properties = MapProperties(isMyLocationEnabled = true),
                uiSettings = MapUiSettings(zoomControlsEnabled = false),
                cameraPositionState = addReportFormViewModel.cameraPositionState
            )
            
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier.size(8.dp),
                    imageVector = Icons.Rounded.Circle,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        } //Box

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @Composable
        fun getAddressFromCoordinates(lat : Double, long : Double) : String {
            val geocoder = Geocoder(LocalContext.current, Locale.getDefault())
            var address : String = ""

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(lat,long, 2) { addresses ->
                    if (addresses.isNotEmpty()) {
                        address = addresses.toString()
                        Log.d("Getting Address", "Resulting address is: $address")
                    }
                }
            }
            return address
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = "Press the"
            )
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .padding(horizontal = 2.dp),
                imageVector = Icons.Rounded.MyLocation,
                contentDescription = null
            )
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = "to use your location as the Report's location"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    } //Column
} //fun LocationOnMap