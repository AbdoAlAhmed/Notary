package com.theideal.data.model

import com.google.firebase.Timestamp

data class Transfer(
    val transferId: String = "",
    var typeOfFinancialTransfer: String = "",
    var amount: Double = 0.0,
    var description: String = "",
    var userId: String = "",
    var contactId: String = "",
    val createAt: Timestamp = Timestamp.now(),
) {
    constructor() : this("","", 0.0, "", "", "", Timestamp.now())
}

