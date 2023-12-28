package com.example.e_alert.main_screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.e_alert.repository.AuthRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

data class UserProfile (
    var email : String = "",
    var firstName : String = "",
    var lastName : String = "",
    var barangay : String = "",
    var street : String = "",
    var phoneNumber : String = ""
)

data class Report (
    val reportID: String = "",
    val timestamp: Timestamp?,
    val reportType : String = "",
    val hazardStatus : String = "",
    val streetOrLandmark : String = "",
    val barangay : String = "",
    val reportDescription : String = ""
)

data class EditProfileErrorState (
    var emptyFirstNameField : EditProfileError? = null,
    var emptyLastNameField : EditProfileError? = null,
    var emptyStreetField : EditProfileError? = null,
    var emptyBarangayField : EditProfileError? = null,
    var invalidPhoneNumberField : EditProfileError? = null,
)

sealed class ProfileChangeState () {
    object SavingChanges : ProfileChangeState()
    object ChangesAlreadySaved : ProfileChangeState()
    object ChangesNotYetSaved : ProfileChangeState()
}

sealed class EditProfileError (var message : String) {
    object EmptyFirstNameField : EditProfileError("First Name field cannot be empty")
    object EmptyLastNameField : EditProfileError("Last Name field cannot be empty")
    object EmptyStreetField : EditProfileError("Street field cannot be empty")
    object EmptyBarangayField : EditProfileError("Barangay field cannot be empty")
    object InvalidPhoneNumberField :
        EditProfileError("Type the correct 10-digit number without the 0 in the beginning. " +
                "Field cannot be empty.")
}

class ProfilePageViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val currentUser = AuthRepository().getUserId()

    init {
        retrieveUserProfile()
        retrieveBarangayListFromDB()
        retrieveCurrentUserReports()
    }

    private var isInEditMode by mutableStateOf(false)

    private var _profileChangeState : ProfileChangeState = ProfileChangeState.ChangesAlreadySaved

    var editProfileErrorState by mutableStateOf(EditProfileErrorState())
        private set

    var profileChangeState by mutableStateOf(_profileChangeState)
        private set

    val userProfileState by mutableStateOf(UserProfile())

    fun startEditMode () {
        isInEditMode = true
    }

    fun doneEditingMode () {
        isInEditMode = false
    }

    fun editModeState () : Boolean {
        return isInEditMode
    }

    fun checkFirstNameFieldError (firstNameField : String) {
        if (firstNameField.isEmpty())
            editProfileErrorState.emptyFirstNameField = EditProfileError.EmptyFirstNameField
    }

    fun checkLastNameFieldError (lastNameField : String) {
        if (lastNameField.isEmpty())
            editProfileErrorState.emptyFirstNameField = EditProfileError.EmptyLastNameField
    }

    fun checkStreetFieldError (barangayField : String) {
        if (barangayField.isEmpty())
            editProfileErrorState.emptyFirstNameField = EditProfileError.EmptyStreetField
    }

    fun checkBarangayFieldError (barangayField : String) {
        if (barangayField.isEmpty())
            editProfileErrorState.emptyFirstNameField = EditProfileError.EmptyBarangayField
    }

    fun checkPhoneNumberFieldError (phoneNumber: String) {
        if (phoneNumber.isEmpty() || phoneNumber.length < 10)
            editProfileErrorState.emptyFirstNameField = EditProfileError.InvalidPhoneNumberField
    }

    fun hasNoFieldErrors() : Boolean {
        return (editProfileErrorState.emptyFirstNameField == null &&
            editProfileErrorState.emptyLastNameField == null &&
            editProfileErrorState.emptyStreetField == null &&
            editProfileErrorState.emptyBarangayField == null &&
            editProfileErrorState.invalidPhoneNumberField == null)
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

    private fun retrieveUserProfile() {
        db.collection("User").document(currentUser).get()
            .addOnSuccessListener { document ->
                userProfileState.firstName =  document["First_Name"].toString()
                userProfileState.lastName = document["Last_Name"].toString()
                userProfileState.email = document["Email"].toString()
                userProfileState.street = document["Street"].toString()
                userProfileState.barangay = document["Barangay"].toString()
                userProfileState.phoneNumber = document["Phone_Number"].toString()
            }
    }

    private val reportsList = mutableStateListOf<Report>()

    fun reportsList() : List<Report> {
        return reportsList
    }

    private fun retrieveCurrentUserReports() {
        db.collection("Report")
            .whereEqualTo("User_ID", currentUser)
            .whereEqualTo("Timestamp", Timestamp.now().toDate())
            .addSnapshotListener { documents, error ->
                documents?.documentChanges?.forEach { change ->
                    when (change.type) {
                        DocumentChange.Type.ADDED -> {
                            reportsList.add(
                                Report(
                                    reportID = change.document["Report_ID"].toString(),
                                    reportDescription = change.document["ReportDescription"].toString(),
                                    reportType = change.document["Report_Hazard_Type"].toString(),
                                    streetOrLandmark = change.document["street_landmark"].toString(),
                                    barangay = change.document["Barangay"].toString(),
                                    hazardStatus = change.document["Report_Status"].toString(),
                                    timestamp = change.document["Timestamp"] as Timestamp,
                                )
                            )
                        } //ADDED

                        DocumentChange.Type.REMOVED -> {
                            reportsList.removeIf { it.reportID == change.document["Report_ID"] }
                        } //REMOVED

                        else -> {  }
                    } //when statement
                } //.forEach
            } //.addSnapshotListener
    }

    fun publishChanges() {
        profileChangeState = ProfileChangeState.SavingChanges

        db.collection("User").document(currentUser).update(
            mapOf(
                "First_Name" to userProfileState.firstName,
                "Last_Name" to userProfileState.lastName,
                "Street" to userProfileState.street,
                "Barangay" to userProfileState.barangay,
                "Phone_Number" to userProfileState.phoneNumber
            )
        ).addOnSuccessListener {
            profileChangeState = ProfileChangeState.ChangesAlreadySaved
        }.addOnFailureListener {
            profileChangeState = ProfileChangeState.ChangesNotYetSaved
        }
    } //db.collection
} //ProfilePageViewModel