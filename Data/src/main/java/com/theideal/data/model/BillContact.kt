package com.theideal.data.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
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
    var theBillOtherFees: Double? = 0.0,
    var companyFees: Double? = 0.0,
    var totalMoney: Double? = 0.0,
    var discount: Double? = 0.0,
    var createAt: Timestamp = Timestamp.now(),
    var updateAt: String,
    private var _remainingMoney: Double? = 0.0,

    ) : Parcelable, BaseObservable() {

    val allFees
        get() = companyFees!! + theBillOtherFees!!


    val totalMoneyCalculate
        get() = grossMoney!! + allFees

    val remainingMoney
        get() = totalMoneyCalculate - paidMoney!!


    val deptCalculate
        get() = totalMoneyCalculate - (paidMoney!! + discount!!)

    fun setStatus(): String {
        return if (deptCalculate <= 0.0) {
            "closed"
        } else if (deptCalculate > 0.0) {
            "deferred"
        } else {
            "open"
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
        "", "", "", "", status, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        0.0, Timestamp.now(), "", 0.0
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
        theBillOtherFees,
        0.0,
        0.0,
        0.0,
        Timestamp.now(),
        "",
        0.0
    )


}


/*
fun calculateGrossMoney() {
    grossMoney.set(totalMoney.get()!! + discount.get()!!)
}

fun calculateDebt() {
    debt.set(totalMoney.get()!! - paidMoney.get()!!)
}

fun calculateDiscount() {
    discount.set(grossMoney.get()!! - totalMoney.get()!!)
}

fun calculatePaidMoney() {
    paidMoney.set(totalMoney.get()!! - debt.get()!!)
}

fun calculateOtherFees() {
    otherFees.set(totalMoney.get()!! - paidMoney.get()!!)
}

fun calculateTotalMoney() {
    totalMoney.set((grossMoney.get()!! + otherFees.get()!!) - (paidMoney.get()!! + discount.get()!!))
}

// return the calculated money
fun calculateAmount(): Double {
    return totalMoney.get()!! - paidMoney.get()!!
}

// set status to deferred if debt > 0
fun setStatus(): String {
    return if ((totalMoney.get()!! - paidMoney.get()!!) > 0) {
        "deferred"
    } else if ((totalMoney.get()!! - paidMoney.get()!!) == 0.0) {
        "closed"
    } else {
        "open"
    }
}
*/