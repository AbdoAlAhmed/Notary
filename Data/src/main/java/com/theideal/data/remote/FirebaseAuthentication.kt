package com.theideal.data.remote

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import timber.log.Timber

open class FirebaseAuthentication() {
    private val mAuth = FirebaseAuth.getInstance()
    val currentUser = mAuth.currentUser


    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        result: (String) -> Unit,
    ) {
        try {
            mAuth.signInWithEmailAndPassword(email, password).addOnCanceledListener {
                result("something went wrong")
            }.addOnSuccessListener {
                result("success")
            }.addOnFailureListener {
                result("something went wrong")
            }
        } catch (e: FirebaseAuthInvalidUserException) {
            result("EmailNotFound")
        } catch (e: FirebaseNetworkException) {
            result("check your internet connection")
        } catch (e: FirebaseException) {
            result("something went wrong")

        } catch (e: FirebaseAuthInvalidCredentialsException) {
            result("invalid email or password")
        } catch (e: FirebaseAuthException) {
            result("something went wrong")
        } catch (e: Exception) {
            result("something went wrong")

        } catch (e: Error) {
            result("something went wrong")

        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        result: (String) -> Unit,
    ) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCanceledListener {
                result("something went wrong")
            }.addOnSuccessListener {
                result("success")
            }.addOnFailureListener {
                if (it is FirebaseAuthUserCollisionException) {
                    result("EmailUsed")
                } else {
                    result("something went wrong")
                }
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            result("EmailUsed")
        } catch (e: FirebaseNetworkException) {
            result("check your internet connection")
        } catch (e: FirebaseException) {
            result("something went wrong")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            result("invalid email")
        } catch (e: FirebaseAuthWeakPasswordException) {
            result("weak password")
        } catch (e: FirebaseAuthException) {
            result("something went wrong")

        } catch (e: Exception) {
            result("something went wrong")

        } catch (e: Error) {
            result("something went wrong")
        }
    }

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }

    fun sigInWithPhoneNumber(phoneNumber: String) {
        Timber.i("sigInWithPhoneNumber")
    }

}