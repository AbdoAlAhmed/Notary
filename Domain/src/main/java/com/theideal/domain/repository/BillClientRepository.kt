package com.theideal.domain.repository

import com.theideal.data.model.Item
import com.theideal.data.model.PayBook
import com.theideal.data.remote.FirebaseBillClient

class BillClientRepository(private val firebaseBillClient: FirebaseBillClient) {

    suspend fun checkIfBillIsOpen(contactId: String) =
        firebaseBillClient.checkIfBillOpen(contactId)

    suspend fun createBillClient(contactId: String) =
        firebaseBillClient.createBillClient(contactId)

    suspend fun getItemsByBillId(billId: String) =
        firebaseBillClient.getItemsToBillClientWithBillId(billId)

    suspend fun getBillsContact(contactId: String) =
        firebaseBillClient.getBillsByContactId(contactId)


    suspend fun updateBillClient(billId: String, keyValue: Map<String, Any>) {
        firebaseBillClient.updateBillClient(billId, keyValue)
    }

    suspend fun deleteBillClient(billId: String) {
        firebaseBillClient.deleteBillClient(billId)
    }

    suspend fun addItemToBillClientWithBillId(billId: String, item: Item) {
        firebaseBillClient.addItemToBillClientWithBillId(billId, item)
    }

    suspend fun updateItemToBillClientWithBillId(billId: String, item: Item) {
        firebaseBillClient.updateItemToBillClientWithBillId(billId, item)
    }

    suspend fun deleteItemToBillClientWithBillId(billId: String, itemId: String) {
        firebaseBillClient.deleteItemToBillClientWithBillId(billId, itemId)
    }

    suspend fun addPayBookToBill(billId: String, payBook: PayBook) {
        firebaseBillClient.addPayBookToBill(billId, payBook)
    }

    suspend fun updatePayBookOnBill(
        billId: String,
        payBookId: String,
        payBook: Map<String, PayBook>
    ) {
        firebaseBillClient.updatePayBookToBillClientWithBillId(billId, payBookId, payBook)
    }

    suspend fun deletePayBookFromBill(billId: String, payBookId: String) {
        firebaseBillClient.deletePayBookToBillClientWithBillId(
            billId = billId,
            payBookId = payBookId
        )
    }

    suspend fun getPayBooksFromBill(billId: String) =
        firebaseBillClient.getPayBooksToBillClientWithBillId(billId)
}