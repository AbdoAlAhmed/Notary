package com.theideal.domain.repository

import com.theideal.data.model.Item
import com.theideal.data.model.PayBook
import com.theideal.data.remote.FirebaseBillClient

class BillClientRepository(private val firebaseBillClient: FirebaseBillClient) {



    suspend fun createBillClient(contactId: String) =
        firebaseBillClient.createBillClient(contactId)



    suspend fun getBillsContact(contactId: String) =
        firebaseBillClient.getBillsByContactId(contactId)
    suspend fun getBillsUser() = firebaseBillClient.getBillsByUserId()

    suspend fun getBillByBillId(billId: String) = firebaseBillClient.getBillByBillId(billId)

    suspend fun updateBillClient(billId: String, keyValue: Map<String, Any>) {
        firebaseBillClient.updateBillClient(billId, keyValue)
    }

    suspend fun deleteBillClient(billId: String) {
        firebaseBillClient.deleteBillClient(billId)
    }
    suspend fun deleteAllBillClient(clientId: String){
        firebaseBillClient.deleteAllBillClient(clientId)
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