package com.theideal.domain.repository

import com.theideal.data.model.BillContact
import com.theideal.data.remote.FirebaseBillSupplier

class BillSupplierRepository(private val firebaseBillSupplier: FirebaseBillSupplier) {

    suspend fun checkIfBillIsOpen(contactId: String) =
        firebaseBillSupplier.checkIfBillOpen(contactId)

    suspend fun createBillSupplier(contactId: String) =
        firebaseBillSupplier.createBillSupplier(contactId)


    suspend fun getBillsSupplier(contactId: String) =
        firebaseBillSupplier.getBillSupplier(contactId)




    suspend fun updateBillSupplier(keyValue: Map<String, Double?>, billId: String) {
        firebaseBillSupplier.updateBillSupplier(keyValue, billId)
    }

    suspend fun deleteBillSupplier(billSupplier: BillContact) {
        firebaseBillSupplier.deleteBillSupplier(billSupplier)
    }





    suspend fun getListOfItemsBillInfo(billId: String) =
        firebaseBillSupplier.getListOfItemBillInfo(billId)



    suspend fun getSuppliersIdIFBillOpen() = firebaseBillSupplier.getSupplierIFBillOpen()

}