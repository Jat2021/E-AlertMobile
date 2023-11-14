package com.example.e_alert.shared_viewModel

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

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
        latitude = 0.0f,
        longitude = 0.0f
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
    val latitude: Double,
    val longitude: Double
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
        viewModelScope.launch {
            db.collection("Report").get()
                .addOnSuccessListener { result ->
                    for (reportDocument in result) {
                        reportsListState.add(
                            ReportData(
                                user = User(
                                    firstName = "Justin",
                                    lastName = "Vasquez",
                                    profilePhoto = null
                                ),
                                images = /*reportDocument["Report_Images"] as List<Uri>*/ null,
                                reportType = reportDocument["Report_Hazard_Type"].toString(),
                                reportDescription = reportDocument["Report_Description"].toString(),
                                reportLocation = Location(
                                    street = reportDocument["Street"].toString(),
                                    baranggay = reportDocument["Baranggay"].toString(),
                                    latitude = /*reportDocument["Latitude"] as Float*/ 0.0f,
                                    longitude = /*reportDocument["Longitude"] as Float*/ 0.0f
                                ),
                                numberOfDislikes = 0,
                                numberOfLikes = 0
                                //hazardStatus = false
                            )
                        )
                    } //result.forEach
                    //allReportsList = reportsListState
                } //.addOnSuccessListener
        } //viewModelScope.lunch
    } //fun retrieveReportsFromDB

//    db.collection("Report").get()
//    .addOnSuccessListener { result ->
//        for (reportDocument in result) {
//            //var author : User? = null
//
//            db.collection("User").document(reportDocument["User_ID"].toString())
//                .get().addOnSuccessListener { userData ->
//                    reportsListState.add(
//                        ReportData(
//                            user = User(
//                                firstName = userData["First_Name"].toString(),
//                                lastName = userData["Last_Name"].toString(),
//                                profilePhoto = userData["Profile_Photo"] as Uri?
//                            ),
//                            images = /*reportDocument["Report_Images"] as List<Uri>*/ null,
//                            reportType = reportDocument["Report_Hazard_Type"].toString(),
//                            reportDescription = reportDocument["Report_Description"].toString(),
//                            reportLocation = Location(
//                                street = reportDocument["Street"].toString(),
//                                baranggay = reportDocument["Baranggay"].toString(),
//                                latitude = /*reportDocument["Latitude"] as Float*/ 0.0f,
//                                longitude = /*reportDocument["Longitude"] as Float*/ 0.0f
//                            ),
//                            numberOfDislikes = 0,
//                            numberOfLikes = 0
//                            //hazardStatus = false
//                        )
//                    )
//                }
//
//
//        } //result.forEach
//        //allReportsList = reportsListState
//    } //.addOnSuccessListener

    // TODO: List of Hazard (retrieve)


}