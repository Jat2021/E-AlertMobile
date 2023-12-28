package com.example.e_alert.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.DateFormat

class AuthRepository {
    fun hasUser() : Boolean {
        return Firebase.auth.currentUser != null
    }

    fun getUserId() : String {
        return Firebase.auth.currentUser?.uid.orEmpty()
    }

    fun getDateCreated() : String {
        Timestamp.now().toDate().time

        return DateFormat.getDateInstance()
            .format(Firebase.auth.currentUser?.metadata!!.creationTimestamp)
    }

    suspend fun createUser(
        email : String,
        password : String,
        onComplete : (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete.invoke(true)
            } else {
                onComplete.invoke(false)
            }
        }.await()
    }!!

    suspend fun login(
        email : String,
        password : String,
        onComplete : (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete.invoke(true)
            } else {
                onComplete.invoke(false)
            }
        }.await()
    }!!
}