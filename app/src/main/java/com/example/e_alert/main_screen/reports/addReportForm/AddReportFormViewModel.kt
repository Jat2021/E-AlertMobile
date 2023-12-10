package com.example.e_alert.main_screen.reports.addReportForm

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_alert.repository.AuthRepository
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.storage.FirebaseStorage
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch
import kotlin.Error
import kotlin.random.Random

data class NewPost (
    var timestamp: ServerTimestamp = ServerTimestamp(),
    var description : String = "",
    var numberOfPersonsInvolved: String? = null,
    var typesOfVehicleInvolved : List<String>? = null,
    var photos : MutableList<Uri> = mutableListOf(),
    var hazardType : String = "",
    var barangay : String = "",
    var streetOrLandmark : String = "",
    var coordinates : GeoPoint = GeoPoint(0.0, 0.0),
    var hazardStatus : String = ""
)

sealed class CreatePostState (var message : String? = null) {
    object Default : CreatePostState()
    object Loading : CreatePostState()
    object Successful : CreatePostState(message = "Successfully created a Report")
    object Failure : CreatePostState(message = "Cannot create a Report")
}

sealed class Error (var message : String) {
    object EmptyDescriptionField : Error("Description is empty")
    object EmptyStreetField : Error("Street is empty")
    object EmptyBarangayField : Error("Barangay is empty")
    object NoReportTypeSelected : Error("No Report Type Selected")
}

class AddReportFormViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    init {
        retrieveBarangayListFromDB()
    }

    private var addReportFormUIState by mutableStateOf(NewPost())

    private var _createPostState : CreatePostState = CreatePostState.Default
    var createPostState = mutableStateOf(_createPostState)

    var pinnedLocationState by mutableStateOf(LatLng(13.621775, 123.194824))


    lateinit var cameraPositionState : CameraPositionState
    
    var isScrollEnabled by mutableStateOf(true)

    fun getHazardType () : String {
        return addReportFormUIState.hazardType
    }

    fun onDescriptionFieldChange(description : String) {
        addReportFormUIState = addReportFormUIState.copy(description = description)
    }

    fun onHazardTypeToggleChange(hazardType : String) {
        addReportFormUIState = addReportFormUIState.copy(hazardType = hazardType)
    }

    fun onStreetOrLandmarkFieldChange(streetOrLandmark : String) {
        addReportFormUIState = addReportFormUIState.copy(streetOrLandmark = streetOrLandmark)
    }

    fun onSelectBarangay(barangay : String) {
        addReportFormUIState = addReportFormUIState.copy(barangay = barangay)
    }

    fun onSelectNumberOfPersonsInvolved(numberOfPersons : String) {
        addReportFormUIState = addReportFormUIState.copy(numberOfPersonsInvolved = numberOfPersons)
    }

    fun onCheckTypeOfVehicle (listOfVehicleType : List<String>) {
        addReportFormUIState = addReportFormUIState.copy(typesOfVehicleInvolved = listOfVehicleType)
    }

    private fun storePhotosToStorage (reportID : String) = viewModelScope.launch {
        selectedPhotos.forEach { uri ->
            uri.let {
                val storage = FirebaseStorage.getInstance().getReference(AuthRepository().getUserId())
                val storageRef = storage.child("$reportID/${uri.lastPathSegment}")

                storageRef.putFile(it)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                addReportFormUIState.photos.add(url)
                            }
                    }
            } //uri.let
        }
    } //fun storePhotosToStorage

    private var selectedPhotos = mutableStateListOf<Uri>()

    fun addPhotosToList (listOfUris : List<Uri>) {
        selectedPhotos.addAll(listOfUris)
    }

    fun removePhotoFromTheList (uri : Uri) {
        selectedPhotos.remove(uri)
    }

    fun getSelectedPhotos () : List<Uri> {
        return selectedPhotos
    }

    fun createPost () {
        val randomNumber = Random.nextInt(90000) + 10000
        val reportID = "RE$randomNumber"

        val reportReference = db.collection("Report").document(reportID)

        storePhotosToStorage(reportID)

        when (addReportFormUIState.hazardType) {
            "Flood" -> {
                reportReference.set(
                    hashMapOf(
                        "User_ID" to AuthRepository().getUserId(),
                        "Report_ID" to reportID,
                        "Timestamp" to FieldValue.serverTimestamp(),
                        "Report_Description" to addReportFormUIState.description,
                        "Report_Images" to addReportFormUIState.photos.toList(),
                        "Report_Hazard_Type" to addReportFormUIState.hazardType,
                        "Street" to addReportFormUIState.streetOrLandmark,
                        "Barangay" to addReportFormUIState.barangay,
                        "Coordinates" to addReportFormUIState.coordinates,
                        "Hazard_Status" to addReportFormUIState.hazardStatus
                    ) //hashMapOf
                ) //db.collection(...).add
                    .addOnSuccessListener {
                        createPostState.value = CreatePostState.Successful
                    }
                    .addOnFailureListener {
                        createPostState.value = CreatePostState.Failure
                    }
            }

            "Road Accident" -> {
                reportReference.set(
                    hashMapOf(
                        "User_ID" to AuthRepository().getUserId(),
                        "Report_ID" to reportID,
                        "Timestamp" to FieldValue.serverTimestamp(),
                        "Report_Description" to addReportFormUIState.description,
                        "NumberOfPersonsInvolved" to addReportFormUIState.numberOfPersonsInvolved,
                        "TypesOfVehicleInvolved" to addReportFormUIState.typesOfVehicleInvolved,
                        "Report_Images" to addReportFormUIState.photos.toList(),
                        "Report_Hazard_Type" to addReportFormUIState.hazardType,
                        "Street" to addReportFormUIState.streetOrLandmark,
                        "Barangay" to addReportFormUIState.barangay,
                        "Coordinates" to addReportFormUIState.coordinates,
                        "Hazard_Status" to addReportFormUIState.hazardStatus
                    ) //hashMapOf
                ) //db.collection(...).add
                    .addOnSuccessListener {
                        createPostState.value = CreatePostState.Successful
                    }
                    .addOnFailureListener {
                        createPostState.value = CreatePostState.Failure
                    }
            } //"Road Accident"

            else -> {

            }
        } //when statement
    }

    val listOfBarangayState = mutableStateListOf<String>()

    private fun retrieveBarangayListFromDB () {
        db.collection("Barangay")
            .orderBy("name", Query.Direction.ASCENDING)
            .addSnapshotListener { result, error ->
                listOfBarangayState.clear()
                for (barangayDocument in result!!.documents)
                    listOfBarangayState.add(
                        barangayDocument["name"].toString()
                    )
            } //.addOnSuccessListener
    } //fun retrieveReportsFromDB

    fun setCoordinatesAsGeopoint(coordinates : LatLng) {
        addReportFormUIState = addReportFormUIState
            .copy(coordinates = GeoPoint(coordinates.latitude,coordinates.longitude))
    }
} //class AddReportFormViewModel