package com.theideal.data.model

import androidx.databinding.BaseObservable
import com.google.firebase.Timestamp

data class Item(
    var itemId: String,
    var supplierId: String,
    var supplierName: String,
    var status: String, // 0 = pending, 1 = paid, 2 = canceled
    var amount: Double,
    var description: String,
    var weight: Double,
    var price: Double,
    var createAt: Timestamp = Timestamp.now(),
    var updateAt: String,
) : BaseObservable() {
    val money: Double
        get() = weight * price


    constructor() : this(
        "",
        "",
        "",
        "",
        0.0,
        "",
        0.0,
        0.0,
        Timestamp.now(),
        ""
    )

    constructor(
        itemId: String,
    ) : this(
        itemId,
        "",
        "",
        "",
        0.0,
        "",
        0.0,
        0.0,
        Timestamp.now(),
        ""
    )
}

