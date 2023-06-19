package com.theideal.data.remote

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.theideal.data.model.User
import kotlinx.coroutines.tasks.await

class FirebaseUser {
    private val db = Firebase.firestore
    private val userCollection = db.collection("users")
    private val currentUser = FirebaseAuth.getInstance().currentUser!!.uid


    suspend fun uploadUserInfo(user: User, result: (String) -> Unit) {
        try {
            val userInfo = userCollection.document(currentUser)
            userInfo.set(user).await()
            userInfo.update("userId", currentUser).await()
            result("success")
        } catch (e: FirebaseNetworkException) {
            result("check your internet connection")
        } catch (e: Exception) {
            result("something went wrong")
        }
    }


    suspend fun getUserInfo(): User? {
        return try {
            val userInfo = userCollection.document(currentUser).get().await()
            if (userInfo.exists()) {
                val user = userInfo.toObject(User::class.java)
                user
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUserInfo(vararg keyValue: String): Result<String> {
        return try {
            val userInfo = userCollection.document(currentUser)
            for (i in keyValue.indices step 2) {
                userInfo.update(keyValue[i], keyValue[i + 1]).await()
            }
            Result.success("success")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}