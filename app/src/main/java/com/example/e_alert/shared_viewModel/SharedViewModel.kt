package com.example.e_alert.shared_viewModel

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query

data class ReportData(
    val user : User = User(
        firstName = "",
        lastName = "",
        profilePhoto = null
    ),
    val reportID: String = "",
    val timestamp: Timestamp?,
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

data class FloodHazardAreaData (
    val hazardAreaID : String = "",
    val address : String = "",
    val barangay : String = "",
    val street : String = "",
    val coordinates: GeoPoint = GeoPoint(0.0, 0.0),
    val riskLevel : String = ""
)

data class AccidentHazardAreaData (
    val hazardAreaID : String = "",
    val address : String = "",
    val barangay : String = "",
    val street : String = "",
    val coordinates: GeoPoint = GeoPoint(0.0, 0.0),
)

class SharedViewModel : ViewModel() {
    val reportsListState = mutableStateListOf<ReportData>()
    val floodHazardAreasListState = mutableStateListOf<FloodHazardAreaData>()
    val accidentHazardAreasListState = mutableStateListOf<AccidentHazardAreaData>()

    val mapState: MutableState<MapState> = mutableStateOf(
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
        db.collection("Report")
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
                                        profilePhoto = null
                                    ),
                                    reportID = reportDocument["Report_ID"].toString(),
                                    timestamp = reportDocument["Timestamp"] as Timestamp?,
                                    images = /*reportDocument["Report_Images"] as List<Uri>*/ null,
                                    reportType = reportDocument["Report_Hazard_Type"].toString(),
                                    reportDescription = reportDocument["Report_Description"].toString(),
                                    reportLocation = Location(
                                        street = reportDocument["Street"].toString(),
                                        baranggay = reportDocument["Baranggay"].toString(),
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
        db.collection("markers")
            .addSnapshotListener { result, error ->

                accidentHazardAreasListState.clear()
                for (hazardAreaDocument in result!!.documents)
                    accidentHazardAreasListState.add(
                        AccidentHazardAreaData(
                            hazardAreaID = hazardAreaDocument["uniqueID"].toString(),
                            address = hazardAreaDocument["address"].toString(),
                            barangay = hazardAreaDocument["barangay"].toString(),
                            street = hazardAreaDocument["street"].toString(),
                            coordinates = hazardAreaDocument["coordinates"] as GeoPoint,
                        )
                    ) //floodHazardAreasListState.add
            } //.addOnSuccessListener
    } //fun retrieveReportsFromDB

    fun retrieveAccidentHazardAreasFromDB() {
        db.collection("Road_Accident_Areas")
            .addSnapshotListener { result, error ->

                floodHazardAreasListState.clear()
                for (hazardAreaDocument in result!!.documents)
                    floodHazardAreasListState.add(
                        FloodHazardAreaData(
                            hazardAreaID = hazardAreaDocument["uniqueID"].toString(),
                            address = hazardAreaDocument["address"].toString(),
                            barangay = hazardAreaDocument["barangay"].toString(),
                            street = hazardAreaDocument["street"].toString(),
                            coordinates = hazardAreaDocument["coordinates"] as GeoPoint,
                            riskLevel = hazardAreaDocument["risk_level"].toString()
                        )
                    ) //floodHazardAreasListState.add
            } //.addOnSuccessListener
    } //fun retrieveReportsFromDB
} //class SharedViewModel