package com.example.e_alert.main_screen.reports

import android.content.Context
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_alert.repository.AuthRepository
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.Error
import kotlin.random.Random

data class NewPost (
    var timestamp: ServerTimestamp = ServerTimestamp(),
    var description : String = "",
    var photos : List<Uri> = emptyList(),
    var hazardType : String = "",
    var barangay : String = "",
    var street : String = "",
    var coordinates : GeoPoint = GeoPoint(0.0, 0.0),
    var hazardStatus : String = "",

    var successfullyCreated : Boolean = false
)

sealed class Error (var message : String) {
    object EmptyDescriptionField : Error("Description is empty")
    object EmptyStreetField : Error("Street is empty")
    object EmptyBarangayField : Error("Barangay is empty")
    object NoReportTypeSelected : Error("No Report Type Selected")
}

class AddReportFormViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private var addReportFormUIState by mutableStateOf(NewPost())

    var pinnedLocationState by mutableStateOf(LatLng(13.621775, 123.194824))

    lateinit var cameraPositionState : CameraPositionState
    var isScrollEnabled by mutableStateOf(true)

    fun onDescriptionFieldChange(description : String) {
        addReportFormUIState = addReportFormUIState.copy(description = description)
    }

    fun onHazardTypeToggleChange(hazardType : String) {
        addReportFormUIState = addReportFormUIState.copy(hazardType = hazardType)
    }

    fun onStreetFieldChange(street : String) {
        addReportFormUIState = addReportFormUIState.copy(street = street)
    }

    fun onSelectBarangay(barangay : String) {
        addReportFormUIState = addReportFormUIState.copy(barangay = barangay)
    }

    fun createPost () = viewModelScope.launch {
        val randomNumber = Random.nextInt(90000) + 10000
        val reportID = "RE$randomNumber"

        db.collection("Report").document(reportID).set(
            hashMapOf(
                "User_ID" to AuthRepository().getUserId(),
                "Report_ID" to reportID,
                "Timestamp" to FieldValue.serverTimestamp(),
                "Report_Description" to addReportFormUIState.description,
                "Report_Images" to addReportFormUIState.photos,
                "Report_Hazard_Type" to addReportFormUIState.hazardType,
                "Street" to addReportFormUIState.street,
                "Barangay" to addReportFormUIState.barangay,
                "Coordinates" to addReportFormUIState.coordinates,
                "Hazard_Status" to addReportFormUIState.hazardStatus
            ) //hashMapOf
        ) //db.collection(...).add
            .addOnSuccessListener { addReportFormUIState.successfullyCreated = true }
            .addOnFailureListener { addReportFormUIState.successfullyCreated = false }
    }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    fun getAddressFromCoordinates(lat : Double, long : Double) : String {
//        val geocoder = Geocoder(context, Locale.getDefault())
//        var address : String = ""
//
//        geocoder.getFromLocation(lat,long, 2) { addresses ->
//            if (addresses.isNotEmpty()) {
//                address = addresses[0].getAddressLine(1)
//            }
//        }
//
//        Log.d("Getting Address", "Resulting address is: $address")
//        return address
//    }

    fun setCoordinatesAsGeopoint(coordinates : LatLng) {
        addReportFormUIState = addReportFormUIState
            .copy(coordinates = GeoPoint(coordinates.latitude,coordinates.longitude))
    }

} //class AddReportFormViewModel