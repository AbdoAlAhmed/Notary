package com.theideal.domain.repository

import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.data.model.ItemInfo
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

    suspend fun addItemToBillClientFromSupplier(billId: String, item: Item) {
        firebaseBillSupplier.addItemToBillClientFromSupplier(billId, item)
    }

    suspend fun getItemsListBySupplierId(supplierId: String) =
        firebaseBillSupplier.getItemListBySupplierId(supplierId)
    suspend fun addItemBillSupplierInfoWithBillId(billId: String, item: ItemInfo) {
        firebaseBillSupplier.addInfoItemToBillSupplierWithBillId(billId, item)
    }

    suspend fun getListOfItemsBillInfo(billId: String) =  firebaseBillSupplier.getListOfItemBillInfo(billId)


    suspend fun updateItemToBillSupplierWithBillId(billId: BillContact, item: Item) {
        firebaseBillSupplier.updateItemToBillSupplierWithBillId(billId, item)
    }

    suspend fun deleteItemToBillSupplierWithBillId(billId: BillContact, item: Item) {
        firebaseBillSupplier.deleteItemToBillSupplierWithBillId(billId, item)
    }

    suspend fun getSuppliersIdIFBillOpen() = firebaseBillSupplier.getSupplierIdIFBillOpen()

}