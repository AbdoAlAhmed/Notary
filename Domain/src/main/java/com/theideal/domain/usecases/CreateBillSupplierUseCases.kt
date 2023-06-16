package com.theideal.domain.usecases

import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.domain.repository.BillSupplierRepository

class CreateBillSupplierUseCases(private val billRepository: BillSupplierRepository) {


    suspend fun createBill(billContact: BillContact, contact: Contact) {
        if (!billRepository.checkIfBillIsOpen(contact.contactId)) {
            billRepository.createBillSupplier(billContact, contact)
        }
    }

    suspend fun getBills(ContactId: String) =
        billRepository.getBillsSupplier(ContactId)

    suspend fun getLastBill(ContactId: String) =
        billRepository.getLastBillSupplier(ContactId)

    suspend fun updateBill(keyValue: Map<String, Any>, billId: String) {
        billRepository.updateBillSupplier(keyValue, billId)
    }

    suspend fun deleteBill(billContact: BillContact) {
        billRepository.deleteBillSupplier(billContact)
    }

    suspend fun addItemToBillAndUpdateBillInfo(billContact: String, item: Item) {
        billRepository.addItemToBillSupplierWithBillId(billContact, item)
        val updateBillInfo = mapOf(
            "amount" to item.amount
        )
        billRepository.updateBillSupplier(keyValue = updateBillInfo, billId = billContact)
    }

    suspend fun updateItemToBill(billContact: BillContact, item: Item) {
        billRepository.updateItemToBillSupplierWithBillId(billContact, item)
    }

    suspend fun deleteItemToBill(billContact: BillContact, item: Item) {
        billRepository.deleteItemToBillSupplierWithBillId(billContact, item)
    }


}