package com.theideal.data.model

import com.google.firebase.Timestamp

data class PayBook(val payBookId: String,var amount: Double, val history: Timestamp) {
    constructor() : this("",0.0, Timestamp.now())

}
