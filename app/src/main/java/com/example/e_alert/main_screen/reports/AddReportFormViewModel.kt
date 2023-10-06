package com.example.e_alert.main_screen.reports

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class NewPost (
    var description : String = "",
    var photos : List<Uri> = emptyList(),
    var reportType : String = "",
    var baranggay : String = "",
    var street : String = "",
)

sealed class Error (var message : String) {
    object EmptyDescriptionField : Error("Description is empty")
    object EmptyStreetField : Error("Street is empty")
    object NoReportTypeSelected : Error("No Report Type Selected")
}

class AddReportFormViewModel : ViewModel() {

    private val db = Firebase.firestore

    private var addReportFormUIState by mutableStateOf(NewPost())

    fun onDescriptionFieldChange(description : String) {
        addReportFormUIState = addReportFormUIState.copy(description = description)
    }

    fun onReportTypeFieldChange(reportType : String) {
        addReportFormUIState = addReportFormUIState.copy(reportType = reportType)
    }

    fun onStreetFieldChange(street : String) {
        addReportFormUIState = addReportFormUIState.copy(street = street)
    }

    fun onSelectBaranggay(baranggay : String) {
        addReportFormUIState = addReportFormUIState.copy(baranggay = baranggay)
    }

    fun createPost () {
        db.document("")
    }
}