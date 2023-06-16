package com.theideal.data.model

import com.google.firebase.Timestamp

data class AdditionalTransactionsFees(
    val additionalTransactionsId: String,
    var billId: String,
    var userId: String,
    var companyId: String,
    var transportFees: String,
    var liftCoast: String,
    var otherFees: String,
    var warranty: String,
    var commission: String,
    var createAt: Timestamp = Timestamp.now(),
    var updateAt: String,
) {
    constructor() : this("", "", "", "", "", "", "", "", "", Timestamp.now(), "")
}