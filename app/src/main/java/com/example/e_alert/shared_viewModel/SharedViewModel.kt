package com.example.e_alert.shared_viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query

data class ReportData(
    val user : User = User(
        firstName = "",
        lastName = "",
        userID = ""
    ),
    val reportID: String = "",
    val timestamp: Timestamp?,
    val images : List<Uri>? = null,
    val reportType : String = "",
    val hazardStatus : String = "Ongoing",
    val numberOfPersonsInvolved : String? = null,
    val typesOfVehicleInvolved : List<String>? = null,
    val reportLocation : Location = Location(
        streetOrLandmark = "",
        barangay = "",
        coordinates = GeoPoint(0.0,0.0)
    ),
    val reportDescription : String = "",
    val numberOfLikes : Int = 0,
    val numberOfDislikes : Int = 0
)

data class User(
    val firstName : String,
    val lastName : String,
    val userID : String
)

data class Location(
    val streetOrLandmark: String,
    val barangay: String,
    val coordinates: GeoPoint
)

data class FloodHazardAreaData (
    val hazardAreaID : String = "",
    val address : String = "",
    val barangay : String = "",
    val streetOrLandmark : String = "",
    val coordinates: GeoPoint = GeoPoint(0.0, 0.0),
    val riskLevel : String = ""
)

data class AccidentHazardAreaData (
    val hazardAreaID : String = "",
    val address : String = "",
    val barangay : String = "",
    val streetOrLandmark : String = "",
    val coordinates: GeoPoint = GeoPoint(0.0, 0.0)
)

data class FloodRiskLevel (
    val min : Double = 0.0,
    val max : Double = 0.0,
    val number : Int
)

data class ReportImage (
    val reportID : String,
    val url : Uri
)

class SharedViewModel : ViewModel() {
    val reportsListState = mutableStateListOf<ReportData>()
    val floodHazardAreasListState = mutableStateListOf<FloodHazardAreaData>()
    val accidentHazardAreasListState = mutableStateListOf<AccidentHazardAreaData>()


    private val db = FirebaseFirestore.getInstance()

    private lateinit var locationCallback : LocationCallback
    private lateinit var fusedLocationProvider : FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    @Composable
    fun getUserCurrentLocation (context : Context) : LatLng {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

        var currentUserLocation by remember {
            mutableStateOf(LatLng(0.0, 0.0))
        }

        DisposableEffect(key1 = fusedLocationProvider) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    for (location in result.locations) {
                        currentUserLocation = LatLng(location.latitude, location.longitude)
                    }

                    fusedLocationProvider.lastLocation
                        .addOnSuccessListener { location ->
                            location?.let {
                                val lat = location.latitude
                                val long = location.longitude

                                currentUserLocation = LatLng(lat, long)
                            }
                        }
                        .addOnFailureListener {
                            Log.e("Error getting location", "${it.message}")
                        }
                }
            } //locationCallback = object : LocationCallback

            if (hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )) {
                locationUpdate()
            } else {
                askPermissions(
                    context, PackageManager.PERMISSION_GRANTED,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }

            onDispose {
                stopLocationUpdate()
            }
        } //DisposableEffect

        return currentUserLocation
    }

    private fun hasPermissions (context : Context, vararg permissions : String) =
        permissions.all { permission ->
            ActivityCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_GRANTED
        }

    private fun askPermissions (context : Context, requestCode: Int, vararg permissions : String) {
        ActivityCompat.requestPermissions(context as Activity, permissions, requestCode)
    }

    private fun stopLocationUpdate() {
        try {
            val removeTask = fusedLocationProvider.removeLocationUpdates(locationCallback)

            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) Log.d("LOCATION_TAG", "Location callback removed.")
                else Log.d("LOCATION_TAG", "Failed to remove location callback.")
            }
        }
        catch (se : SecurityException) {
            Log.e("LOCATION_TAG", "Fail to remove location callback... $se")
        }
    }

    @SuppressLint("MissingPermission")
    private fun locationUpdate () {
        locationCallback.let {
            val locationRequest =
                com.google.android.gms.location.LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY, 300)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(3000)
                    .setMaxUpdateDelayMillis(100)
                    .build()

            fusedLocationProvider.requestLocationUpdates(
                locationRequest, it, Looper.getMainLooper()
            )
        }
    }

//    private fun retrievePhotosFromDB(reportID : String) : List<Uri>? {
//        val reportImageReference = db.collection("Report_Image")
//        val images : MutableList<Uri>? = null
//
//        reportImageReference.where(Filter.equalTo("Report_ID",reportID))
//            .addSnapshotListener { result, error ->
//                for (photo in result!!.documents) {
//                    if (result.documents.isNotEmpty())
//                        images?.add(photo["Url_from_storage"] as Uri)
//                    Log.d("DISPLAY IMAGE", "data.images[index]: ${photo["Url_from_storage"]}")
//                }
//            }
//        return images
//    }

    fun retrieveReportsFromDB() {
        val reportImageReference = db.collection("Report_Image")
        val images : MutableList<Uri>? = null

        db.collection("Report")
            .whereLessThanOrEqualTo("Timestamp", Timestamp.now())
            .orderBy("Timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { report, error ->
                for (reportDocument in report!!.documents) {
                    db.collection("User").document(reportDocument["User_ID"].toString())
                        .addSnapshotListener() { user, errors ->
                            reportsListState.add(
                                ReportData(
                                    user = User(
                                        firstName = user!!["First_Name"].toString(),
                                        lastName = user["Last_Name"].toString(),
                                        userID = reportDocument["User_ID"].toString()
                                    ),
                                    reportID = reportDocument["Report_ID"].toString(),
                                    timestamp = reportDocument["Timestamp"] as Timestamp?,
                                    images = images,
                                    reportType = reportDocument["Report_Hazard_Type"].toString(),
                                    hazardStatus = reportDocument["Hazard_Status"].toString(),
                                    reportDescription = reportDocument["Report_Description"].toString(),
                                    numberOfPersonsInvolved = reportDocument["NumberOfPersonsInvolved"].toString(),
                                    typesOfVehicleInvolved = reportDocument["TypesOfVehicleInvolved"] as List<String>?,
                                    reportLocation = Location(
                                        streetOrLandmark = reportDocument["street_landmark"].toString(),
                                        barangay = reportDocument["Barangay"].toString(),
                                        coordinates = reportDocument["Coordinates"] as GeoPoint
                                    ),
                                    numberOfDislikes = 0,
                                    numberOfLikes = 0
                                    //hazardStatus = false
                                )
                            ) //reportsListState.add


                        }
                }
                reportsListState.clear()
            } //.addOnSuccessListener
    } //fun retrieveReportsFromDB

    fun retrieveFloodHazardAreasFromDB() {
        db.collection("Flood_Hazard_Area").addSnapshotListener { result, error ->
            floodHazardAreasListState.clear()

            for (hazardAreaDocument in result!!.documents)
                floodHazardAreasListState.add(
                    FloodHazardAreaData(
                        hazardAreaID = hazardAreaDocument["uniqueID"].toString(),
                        address = hazardAreaDocument["address"].toString(),
                        barangay = hazardAreaDocument["barangay"].toString(),
                        streetOrLandmark = hazardAreaDocument["street_landmark"].toString(),
                        coordinates = hazardAreaDocument["coordinates"] as GeoPoint,
                        riskLevel = hazardAreaDocument["risk_level"].toString()
                    )
                ) //floodHazardAreasListState.add
        } //.addOnSuccessListener
    } //fun retrieveFloodHazardAreasFromDB

    fun retrieveAccidentHazardAreasFromDB() {
        db.collection("Road_Accident_Areas").addSnapshotListener { result, error ->
            accidentHazardAreasListState.clear()

            for (hazardAreaDocument in result!!.documents)
                accidentHazardAreasListState.add(
                    AccidentHazardAreaData(
                        hazardAreaID = hazardAreaDocument["uniqueID"].toString(),
                        address = hazardAreaDocument["address"].toString(),
                        barangay = hazardAreaDocument["barangay"].toString(),
                        streetOrLandmark = hazardAreaDocument["street_landmark"].toString(),
                        coordinates = hazardAreaDocument["coordinates"] as GeoPoint,
                    )
                ) //accidentHazardAreasListState
            } //.addOnSuccessListener
    } //fun retrieveAccidentHazardAreasFromDB

    var floodRiskLevelState = mutableStateListOf<FloodRiskLevel>()

    fun retrieveFloodRiskLevel() {
        db.collection("Flood_Risk_Level").addSnapshotListener { result, error ->
            for (floodRiskLevel in result!!.documents) {
                floodRiskLevelState.add(
                    FloodRiskLevel(
                        min = floodRiskLevel["min"] as Double,
                        max = floodRiskLevel["max"] as Double,
                        number = floodRiskLevel["number"] as Int
                    )
                )
            }
        }
    }

    var isDeleting = mutableStateOf(false)

    fun deleteReport (reportID : String) {
        isDeleting.value = true

        db.collection("Report").document(reportID).delete()
            .addOnSuccessListener { isDeleting.value = false }
    }

    var photosFromDB = mutableStateListOf<ReportImage>()

    fun retrievePhotosFromDB () {
        val reportImagesReference = db.collection("Report_Images")
        val reportReference = db.collection("Report")

        reportImagesReference.get().addOnSuccessListener {
            for (uri in it.documents) {
                photosFromDB.add(ReportImage(
                    uri["Report_ID"].toString(),
                    uri["Url_from_storage"] as Uri)
                )
            }
        }
    }
} //class SharedViewModel