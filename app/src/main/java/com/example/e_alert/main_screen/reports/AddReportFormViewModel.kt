package com.example.e_alert.main_screen.reports

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_alert.repository.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

data class NewPost (
    var description : String = "",
    var photos : List<Uri> = emptyList(),
    var hazardType : String = "",
    var baranggay : String = "",
    var street : String = "",
    var coordinate : String = "",

    var successfullyCreated : Boolean = false
)

sealed class Error (var message : String) {
    object EmptyDescriptionField : Error("Description is empty")
    object EmptyStreetField : Error("Street is empty")
    object EmptyBaranggayField : Error("Baranggay is empty")
    object NoReportTypeSelected : Error("No Report Type Selected")
}

class AddReportFormViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private var addReportFormUIState by mutableStateOf(NewPost())

    fun onDescriptionFieldChange(description : String) {
        addReportFormUIState = addReportFormUIState.copy(description = description)
    }

    fun onHazardTypeToggleChange(hazardType : String) {
        addReportFormUIState = addReportFormUIState.copy(hazardType = hazardType)
    }

    fun onStreetFieldChange(street : String) {
        addReportFormUIState = addReportFormUIState.copy(street = street)
    }

    fun onSelectBaranggay(baranggay : String) {
        addReportFormUIState = addReportFormUIState.copy(baranggay = baranggay)
    }

    fun createPost () = viewModelScope.launch {
        db.collection("Report").add(
            hashMapOf(
                "User_ID" to AuthRepository().getUserId(),
                "Report_Description" to addReportFormUIState.description,
                "Report_Images" to addReportFormUIState.photos,
                "Report_Hazard_Type" to addReportFormUIState.hazardType,
                "Location" to mapOf(
                    "Street" to addReportFormUIState.street,
                    "Baranggay" to addReportFormUIState.baranggay,
                    "Coordinate" to addReportFormUIState.coordinate
                ) //mapOf
            ) //hashMapOf
        ) //db.collection(...).add
            .addOnSuccessListener { addReportFormUIState.successfullyCreated = true }
            .addOnFailureListener { addReportFormUIState.successfullyCreated = false }
    }
}