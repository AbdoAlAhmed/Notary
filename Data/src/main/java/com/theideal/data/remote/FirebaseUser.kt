package com.theideal.data.remote

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.User
import kotlinx.coroutines.tasks.await

class FirebaseUser {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")
    private val currentUser = FirebaseAuth.getInstance().currentUser


    suspend fun uploadUserInfo(user: User, result: (String) -> Unit) {
        try {
            userCollection.document(currentUser!!.uid).set(user)
                .addOnSuccessListener {
                    userCollection.document(currentUser.uid).update("userId", currentUser!!.uid)
                    result("Succeed")
                }.addOnFailureListener {
                    result(it.message.toString())
                }.await()
        } catch (e: FirebaseNetworkException) {
            result("check your internet connection")
        } catch (e: Exception) {
            result(e.message.toString())
        }
    }


    suspend fun getUserInfo(): User? {
        return try {
            val userInfo = userCollection.document(currentUser!!.uid).get().await()
            if (userInfo.exists()) {
                val user = userInfo.toObject(User::class.java)
                user
            } else {
                User()
            }
        } catch (e: Exception) {
            User()
        }
    }

    suspend fun updateUserInfo(vararg keyValue: String): Result<String> {
        return try {
            val userInfo = userCollection.document(currentUser!!.uid)
            for (i in keyValue.indices step 2) {
                userInfo.update(keyValue[i], keyValue[i + 1]).await()
            }
            Result.success("success")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

     fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}