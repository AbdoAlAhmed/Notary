package com.theideal.data.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BillContact(
    var billId: String,
    var userId: String,
    var contactId: String,
    var note: String,
    var status: String, // open, closed, deferred
    var grossMoney: Double? = 0.0,
    var paidMoney: Double? = 0.0,
    var payMoney: Double? = 0.0,
    var amount: Double? = 0.0,
    var totalWeight: Double? = 0.0,
    var theBillOtherFees: Double? = 0.0,
    var companyFees: Double? = 0.0,
    var discount: Double? = 0.0,
    var createAt: Timestamp = Timestamp.now(),
    var updateAt: String,

    ) : Parcelable, BaseObservable() {

    val allFees
        get() = companyFees!! + theBillOtherFees!!


    val totalMoneyCalculate
        get() = grossMoney!! + allFees

    private val totalPaidMoney
        get() = paidMoney!! + payMoney!!


    val deptCalculate
        get() = totalMoneyCalculate - (totalPaidMoney + discount!!)

    fun setStatus(): String {
        return when {
            totalMoneyCalculate <= totalPaidMoney!! -> "closed"
            totalMoneyCalculate > totalPaidMoney!! -> "deferred"
            else -> "open"
        }
    }


    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        Timestamp.now(),
        "",

        )


    constructor(billId: String, userId: String, contactId: String, status: String) : this(
        billId,
        userId,
        contactId,
        "",
        status,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        Timestamp.now(),
        ""
    )

    constructor(
        grossMoney: Double?,
        amount: Double?,
    ) : this(
        "", "", "", "", "", grossMoney,
        0.0, amount, 0.0, 0.0, 0.0, 0.0, 0.0, Timestamp.now(), ""
    )

    constructor(
        status: String
    ) : this(
        "", "", "", "", status, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        Timestamp.now(), ""
    )

    constructor(
        theBillOtherFees: Double?
    ) : this(
        "",
        "",
        "",
        "",
        "",
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        theBillOtherFees,
        0.0,
        0.0,
        Timestamp.now(),
        ""
    )

    constructor(amount: Double?, status: String) : this(
        "", "", "", "", status, 0.0, 0.0, 0.0, amount, 0.0, 0.0, 0.0, 0.0, Timestamp.now(), ""
    )
}


