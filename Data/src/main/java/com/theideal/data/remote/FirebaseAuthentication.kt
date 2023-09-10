package com.theideal.data.remote

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class FirebaseAuthentication() {

    private val mAuth = FirebaseAuth.getInstance()
    private val currentUser = mAuth.currentUser


    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
        result: (String) -> Unit
    ) {
        try {
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                result("Succeed")
            }.addOnFailureListener {
                result(it.message.toString())
            }.await()
        } catch (e: FirebaseNetworkException) {
            result("check your network")
        } catch (e: FirebaseFirestoreException) {
            result("try again")
        } catch (e: Exception) {
            result("try again")
        }

    }

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        result: (String) -> Unit
    ) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                result("Succeed")
            }.addOnFailureListener {
                result(it.message.toString())
            }.await()
        } catch (e: FirebaseNetworkException) {
            result("check your network")
        } catch (e: FirebaseFirestoreException) {
            result("try again")
        } catch (e: Exception) {
            result("try again")
        }
    }

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }

    fun sigInWithPhoneNumber(phoneNumber: String) {
        PhoneAuthOptions.newBuilder().setPhoneNumber(phoneNumber).
            setTimeout(1000,TimeUnit.SECONDS).requireSmsValidation(true)

                .build()
    }


    fun signOut() {
        mAuth.signOut()
    }
}