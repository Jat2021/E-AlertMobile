package com.example.e_alert.main_screen.reports

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_alert.repository.AuthRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
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
    val numberOfDislikes : Int = 0
)

data class User(
    val firstName : String,
    val lastName : String,
    val profilePhoto : Uri?
)

data class Location(
    val street : String,
    val baranggay : String,
    val latitude : Float,
    val longitude : Float
)

class ReportsPageViewModel : ViewModel() {
    val allReportsListState = mutableStateListOf<ReportData>()
    //lateinit var allReportsList : List<ReportData>

    private val db = FirebaseFirestore.getInstance()

    fun getAllReports() = viewModelScope.launch {
        lateinit var author : User

        db.collection("User").document(AuthRepository().getUserId())
            .get().addOnSuccessListener { userData ->
            author = User(
                firstName = userData["First_Name"].toString(),
                lastName = userData["Last_Name"].toString(),
                profilePhoto = /*user["Profile_Photo"] as Uri*/ null
            )
        }

        db.collection("Report").get()
            .addOnSuccessListener { result ->
                for (reportDocument in result) {
                    allReportsListState.add(
                        ReportData(
                            user = author,
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
                            numberOfLikes = 0,
                        )
                    )
                } //result.forEach
                //allReportsList = allReportsListState
            } //.addOnSuccessListener
    }
} //class ReportsPageViewModel

