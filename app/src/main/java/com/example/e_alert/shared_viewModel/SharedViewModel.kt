package com.example.e_alert.shared_viewModel

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

data class ReportData(
    val user : User = User(
        firstName = "",
        lastName = "",
        profilePhoto = null
    ),
    val images : List<Uri>? = null,
    val reportType : String = "",
    val reportLocation : Location = Location(
        street = "",
        baranggay = "",
        coordinates = GeoPoint(0.0,0.0)
    ),
    val reportDescription : String = "",
    val numberOfLikes : Int = 0,
    val numberOfDislikes : Int = 0,
    val isVerified : Boolean = false
)

data class User(
    val firstName : String,
    val lastName : String,
    val profilePhoto : Uri?
)

data class Location(
    val street: String,
    val baranggay: String,
    val coordinates: GeoPoint
)

class SharedViewModel : ViewModel() {
    val reportsListState = mutableStateListOf<ReportData>()

    private val mapState: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
            reportPins = reportsListState
        )
    )

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mapState.value = mapState.value.copy(
                        lastKnownLocation = task.result)
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }

    //TODO: List of Reports pins

    private val db = FirebaseFirestore.getInstance()

    fun retrieveReportsFromDB() {
        db.collection("Report").document()
            .addSnapshotListener { reportDocument, e ->
                reportsListState.add(
                    ReportData(
                        user = User(
                            firstName = "Justin",
                            lastName = "Vasquez",
                            profilePhoto = null
                        ),
                        images = /*reportDocument["Report_Images"] as List<Uri>*/ null,
                        reportType = reportDocument!!["Report_Hazard_Type"].toString(),
                        reportDescription = reportDocument["Report_Description"].toString(),
                        reportLocation = Location(
                            street = reportDocument["Street"].toString(),
                            baranggay = reportDocument["Baranggay"].toString(),
                            coordinates = /*reportDocument["Coordinates"] as GeoPoint*/ GeoPoint(0.0,0.0),
                        ),
                        numberOfDislikes = 0,
                        numberOfLikes = 0
                        //hazardStatus = false
                    )
                )
                //allReportsList = reportsListState
            } //.addOnSuccessListener
    } //fun retrieveReportsFromDB

    // TODO: List of Hazard (retrieve)


}