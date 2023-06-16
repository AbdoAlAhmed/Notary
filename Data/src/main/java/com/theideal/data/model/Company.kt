package com.theideal.data.model

import com.google.firebase.Timestamp


data class Company(
    val companyId: String,
    var companyName: String,
    var address: String,
    var Description: String,
    var phone1: String,
    var phone2: String,
    var bossId: String,
    var Partner_id: String,
    var employers_id: String,
    var updateAt: String,
    var CreateAt: Timestamp,
    var ClientTransactionsFees: String,
    var suppliersTransactionsFees: String,
) {
    constructor() : this(
        "", "", "", "", "", "", "", "", "", "",Timestamp.now(),  "", ""
    )

    // constructor with no phone2
    constructor(
        companyId: String,
        companyName: String,
        address: String,
        Description: String,
        phone1: String,
    ) : this(
        companyId,
        companyName,
        address,
        Description,
        phone1,
        "",
        "",
        "",
        "",
        "",
        Timestamp.now(),
        "",
        ""
    )
}
