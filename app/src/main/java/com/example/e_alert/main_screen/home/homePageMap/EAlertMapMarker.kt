package com.example.e_alert.main_screen.home.homePageMap

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun EAlertMapMarker (
    context : Context,
    position : LatLng,
    title : String,
    snippet : String,
    @DrawableRes iconFromRes: Int
) {
    val icon = bitmapDescriptorFromVector(context, iconFromRes)

    Marker(
        state = MarkerState(position),
        title = title,
        icon = icon,
        snippet = snippet
    )
} //fun EAlertMapMarker

fun bitmapDescriptorFromVector (
    context : Context,
    iconFromRes: Int,
) : BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, iconFromRes) ?: return null

    drawable.setBounds(0, 0 , drawable.intrinsicWidth, drawable.intrinsicHeight)

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)

    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
} //fun bitmapDescriptorFromVector