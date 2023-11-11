package com.theideal.domain.repository

import com.theideal.data.model.User
import com.theideal.data.remote.FirebaseUser

class UserRepository(private val firebaseUser: FirebaseUser) {

    suspend fun uploadUserInfo(user: User, result: (String) -> Unit) {
        firebaseUser.uploadUserInfo(user) {
            result(it)
        }
    }

    suspend fun getUserInfo(): User? {
        return firebaseUser.getUserInfo()
    }
    suspend fun updateUserInfo(vararg keyValue: String): Result<String> {
        return firebaseUser.updateUserInfo(*keyValue)
    }

     fun logout() {
        firebaseUser.logout()
    }
}