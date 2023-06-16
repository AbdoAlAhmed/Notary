package com.theideal.domain.repository

import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.data.remote.FirebaseBillSupplier

class BillSupplierRepository(private val firebaseBillSupplier: FirebaseBillSupplier) {

    suspend fun checkIfBillIsOpen(contactId: String) =
        firebaseBillSupplier.checkIfBillOpen(contactId)
    suspend fun createBillSupplier(billSupplier: BillContact, contact: Contact) {
        firebaseBillSupplier.createBillSupplier(billSupplier, contact)
    }

    suspend fun getBillsSupplier(contactId: String) =
        firebaseBillSupplier.getBillSupplier(contactId)

    suspend fun getLastBillSupplier(contactId: String) =
        firebaseBillSupplier.getLastBillSupplier(contactId)


    suspend fun updateBillSupplier(keyValue: Map<String, Any>, billId: String) {
        firebaseBillSupplier.updateBillSupplier(keyValue, billId)
    }

    suspend fun deleteBillSupplier(billSupplier: BillContact) {
        firebaseBillSupplier.deleteBillSupplier(billSupplier)
    }

    suspend fun addItemToBillSupplierWithBillId(billId: String, item: Item) {
        firebaseBillSupplier.addItemToBillSupplierWithBillId(billId, item)
    }

    suspend fun updateItemToBillSupplierWithBillId(billId: BillContact, item: Item) {
        firebaseBillSupplier.updateItemToBillSupplierWithBillId(billId, item)
    }

    suspend fun deleteItemToBillSupplierWithBillId(billId: BillContact, item: Item) {
        firebaseBillSupplier.deleteItemToBillSupplierWithBillId(billId, item)
    }

    suspend fun getSuppliersIdIFBillOpen() = firebaseBillSupplier.getSupplierIdIFBillOpen()

}