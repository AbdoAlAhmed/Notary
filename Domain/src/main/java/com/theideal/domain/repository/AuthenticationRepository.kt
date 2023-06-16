package com.theideal.domain.repository

import com.theideal.data.remote.FirebaseAuthentication

class AuthenticationRepository(private val firebaseAuthentication: FirebaseAuthentication) {

     fun signInWithEmailAndPassword(email: String, password: String, result: (String) -> Unit) {
        firebaseAuthentication.signInWithEmailAndPassword(email, password) {
            result(it)
        }
    }

     fun createUserWithEmailAndPassword(email: String, password: String, result: (String) -> Unit) {
        firebaseAuthentication.createUserWithEmailAndPassword(email, password) {
            result(it)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuthentication.isUserLoggedIn()
    }

    fun sigInWithPhoneNumber(phoneNumber: String) {
        firebaseAuthentication.sigInWithPhoneNumber(phoneNumber)
    }
}