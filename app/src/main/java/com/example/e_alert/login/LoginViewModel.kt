package com.example.e_alert.login

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_alert.repository.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

data class LoginUiState(
    val profilePhoto : Uri? = null,
    val email : String = "",
    val password : String = "",

    val emailSignUp : String = "",
    val passwordSignUp : String = "",
    val confirmPasswordSignUp : String = "",

    val firstName : String = "",
    val lastName : String = "",
    val street : String = "",
    val baranggay : String = "",
    val phoneNumber : String = "",

    val isLoading : Boolean = false,
    val isSuccessLogin : Boolean = false,

    val signUpError : String? = null,
    val loginError : String? = null,

    val hasEmailError : Boolean = false,
    val hasPasswordError : Boolean = false,
    val confirmPasswordMatch : Boolean = true
)

class LoginViewModel(
    private val repository : AuthRepository = AuthRepository()
) : ViewModel() {
    val db = FirebaseFirestore.getInstance()

    val hasUser : Boolean get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange (email : String) {
        loginUiState = loginUiState.copy(email = email)
    }

    fun onEmailSignUpChange (emailSignUp : String) {
        loginUiState = loginUiState.copy(emailSignUp = emailSignUp)
    }

    fun onPasswordChange (password : String) {
        loginUiState = loginUiState.copy(password = password)
    }

    fun onPasswordSignUpChange (passwordSignup : String) {
        loginUiState = loginUiState.copy(passwordSignUp = passwordSignup)
    }

    fun onConfirmPasswordChange (confirmPasswordSignUp : String) {
        loginUiState = loginUiState.copy(confirmPasswordSignUp = confirmPasswordSignUp)
    }

    fun onFirstNameChange (firstName : String){
        loginUiState = loginUiState.copy(firstName = firstName)
    }

    fun onLastNameChange (lastName : String){
        loginUiState = loginUiState.copy(lastName = lastName)
    }

    fun onStreetChange (street : String) {
        loginUiState = loginUiState.copy(street = street)
    }

    fun onBaranggayChange (baranggay: String) {
        loginUiState = loginUiState.copy(baranggay = baranggay)
    }

    fun onPhoneNumberChange (phoneNumber: String) {
        loginUiState = loginUiState.copy(phoneNumber = phoneNumber)
    }

    private fun validateLoginForm () : Boolean {
        return loginUiState.email.isNotBlank() &&
                loginUiState.password.isNotBlank()
    }

    private fun validateSignupForm () : Boolean {
        return loginUiState.emailSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank()
    }

    private fun createUserDocumentInDB () {
        db.collection("User")
            .document(repository.getUserId()).set(
                hashMapOf(
                    "User_Type" to "PUBLIC",
                    "First_Name" to loginUiState.firstName,
                    "Last_Name" to loginUiState.lastName,
                    "Street" to loginUiState.street,
                    "Baranggay" to loginUiState.baranggay,
                    "Phone_Number" to loginUiState.phoneNumber,
                )
            )
    } //createUserDocumentInDB

    val listOfBarangayState = mutableStateListOf<String>()

    fun retrieveBarangayListFromDB () {
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

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignupForm()) {
                throw IllegalArgumentException("Email and Password cannot be empty.")
            }

            loginUiState = loginUiState.copy(isLoading = true)

            if (loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp) {
                throw IllegalArgumentException("Email and Password do not match.")
            }

            loginUiState = loginUiState.copy(signUpError = null)

            repository.createUser(
                loginUiState.emailSignUp,
                loginUiState.passwordSignUp
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    createUserDocumentInDB()

                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Login failed",
                        Toast.LENGTH_SHORT
                    ).show()

                    loginUiState.copy(isSuccessLogin = false)
                }
            } //repository.createUser
        } catch (e:Exception) {
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    } //createUser

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()){
                throw IllegalArgumentException("Email and Password cannot be empty")
            }

            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)

            repository.login(
                loginUiState.email,
                loginUiState.password
            ) { isSuccessful ->
                loginUiState = if (isSuccessful){
                    Toast.makeText(
                        context,
                        "success login",
                        Toast.LENGTH_SHORT
                    ).show()

                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed to login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState.copy(isSuccessLogin = false)
                }
            } //repository.login
        } catch (e:Exception){
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    } //loginUser
}