package com.example.e_alert.main_screen.reports

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.e_alert.repository.AuthRepository
import com.example.e_alert.shared_viewModel.Location
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.shared_viewModel.User
import com.google.firebase.firestore.FirebaseFirestore

//data class ReportData(
//    val user : User = User(
//        firstName = "",
//        lastName = "",
//        profilePhoto = null
//    ),
//    val images : List<Uri>? = null,
//    val reportType : String = "",
//    val reportLocation : Location = Location(
//        street = "",
//        baranggay = "",
//        latitude = 0.0f,
//        longitude = 0.0f
//    ),
//    val reportDescription : String = "",
//    val numberOfLikes : Int = 0,
//    val numberOfDislikes : Int = 0
//)
//
//data class User(
//    val firstName : String,
//    val lastName : String,
//    val profilePhoto : Uri?
//)
//
//data class Location(
//    val street : String,
//    val baranggay : String,
//    val latitude : Float,
//    val longitude : Float
//)

class ReportsPageViewModel(sharedViewModel : SharedViewModel) : ViewModel() {
    val reportsListState = sharedViewModel.reportsListState

    fun getReportsList() : MutableList<ReportData> {
        return reportsListState
    }
} //class ReportsPageViewModel

    val reportsListState = mutableStateListOf<ReportData>()
    //lateinit var allReportsList : List<ReportData>

    private val db = FirebaseFirestore.getInstance()

    fun retrieveReportsFromDB() {
        lateinit var author : User

        db.collection("User").document(AuthRepository().getUserId())
            .get().addOnSuccessListener { userData ->
                author = User(
                    firstName = userData["First_Name"].toString(),
                    lastName = userData["Last_Name"].toString(),
                    profilePhoto = /*user["Profile_Photo"] as Uri*/ null
                )
            }

        db.collection("Report").document()
            .addSnapshotListener { reportDocument, e ->
                //for (reportDocument in result) {
                    reportsListState.add(
                        ReportData(
                            user = author,
                            images = /*reportDocument["Report_Images"] as List<Uri>*/ null,
                            reportType = reportDocument!!["Report_Hazard_Type"].toString(),
                            reportDescription = reportDocument["Report_Description"].toString(),
                            reportLocation = Location(
                                street = reportDocument["Street"].toString(),
                                baranggay = reportDocument["Baranggay"].toString(),
                                latitude = reportDocument["Latitude"] as Double,
                                longitude = reportDocument["Longitude"] as Double
                            ),
                            numberOfDislikes = 0,
                            numberOfLikes = 0,
                        )
                    )
                //} //result.forEach
                //allReportsList = reportsListState
            } //.addOnSuccessListener
    } //fun retrieveReportsFromDB