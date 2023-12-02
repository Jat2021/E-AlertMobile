package com.example.e_alert.main_screen.reports.addReportForm.composableFunctions

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import java.util.Locale

@OptIn(FlowPreview::class, ExperimentalComposeUiApi::class)
@Composable
fun LocationOnMap (addReportFormViewModel: AddReportFormViewModel?) {
    LaunchedEffect(addReportFormViewModel!!.cameraPositionState.position.target) {
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            GoogleMap(
                modifier = Modifier
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
                cameraPositionState = addReportFormViewModel.cameraPositionState
            )

            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null
                )
            }
        } //Box

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun Context.getAddressFromCoordinates(lat : Double, long : Double) : String {
            val geocoder = Geocoder(this, Locale.getDefault())
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

        TextButton(
            shape = MaterialTheme.shapes.small,
            onClick = { /*TODO: Get Current Location coordinates and equivalent address*/ },
        ) {
            Text(
                color = MaterialTheme.colorScheme.tertiary,
                text = "Use my current location"
            )
            Icon(
                imageVector = Icons.Rounded.MyLocation,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Use my current location"
            )
        }
    }
}