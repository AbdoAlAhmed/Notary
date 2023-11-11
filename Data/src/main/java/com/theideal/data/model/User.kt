package com.theideal.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.security.MessageDigest

@Parcelize
data class User(
    val userId: String,
    var name: String,
    var phone: String,
    var email: String,
    private var password: String,
    var companyId: String = "",
    var createdAt: Timestamp = Timestamp.now(),
    var updatedAt: String = ""
) : Parcelable {
    constructor() : this("", "", "", "", "")

    fun getPassword(): String = password
    fun setPassword(password: String) {
        this.password = password
//        // Generate a random salt
//        val salt = "randomSalt123"
//        // Combine the password and salt
//        val passwordWithSalt = password + salt
//        // Use SHA-256 for hashing
//        val digest = MessageDigest.getInstance("SHA-256")
//        val hash = digest.digest(passwordWithSalt.toByteArray())
//        // Convert the byte array to a base64-encoded string
//        val base64Hash = hash.fold("") { str, it -> str + "%02x".format(it) }
//        this.password = base64Hash
    }




}
