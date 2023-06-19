package com.theideal.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

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
    }
}
