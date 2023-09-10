package com.theideal.data.model

data class ItemWrapper(
    val billContact: BillContact,
    val contact: Contact
) {
    constructor() : this(billContact = BillContact(), contact = Contact())
}
