package com.theideal.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var contactId: String,
    var userId: String,
    var contactRef:String,
    var name: String,
    var phone: String,
    var createAt: Timestamp = Timestamp.now(),
    var updateAt: String,
): Parcelable {
    constructor() : this("","", "", "", "", Timestamp.now(), "")
}