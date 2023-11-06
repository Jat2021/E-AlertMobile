package com.example.e_alert.shared_viewModel

import android.location.Location

data class MapState(
    val lastKnownLocation: Location?,
    val reportPins: List<ReportData>
)