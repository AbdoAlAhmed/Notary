package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.BillContact
import com.theideal.data.model.Item
import com.theideal.data.model.PayBook
import kotlinx.coroutines.tasks.await

class FirebaseBillClient {
    private val db = FirebaseFirestore.getInstance()
    private val billClientRef = db.collection("BillClient")
    private val userUid = FirebaseAuth.getInstance().currentUser?.uid

    suspend fun checkIfBillOpen(contactId: String): List<BillContact> {
        val bill = billClientRef
            .whereEqualTo("userId", userUid)
            .whereEqualTo("contactId", contactId)
            .get().await()
        return bill!!.toObjects(BillContact::class.java)
    }

    suspend fun createBillClient(contactId: String): BillContact {
        var billId = ""
        billClientRef.add(BillContact()).addOnSuccessListener {
            billClientRef.document(it.id).update(
                "billId", it.id, "userId", userUid,
                "contactId", contactId, "status", "open"
            )
            billId = it.id
        }.await()
        return BillContact(
            billId,
            userUid!!,
            contactId,
            "open",
        )
    }

    suspend fun getBillsByContactId(contactId: String): List<BillContact> {
        val bill =
            billClientRef.whereEqualTo("userId", userUid)
                .whereEqualTo("contactId", contactId)
                .get()
                .await()
        return bill!!.toObjects(BillContact::class.java)
    }

    suspend fun updateBillClient(billId: String, keyValue: Map<String, Any>) {
        for ((key, value) in keyValue) {
            billClientRef.document(billId).update(
                key, value
            ).await()
        }
    }

    suspend fun deleteBillClient(billId: String) {
        billClientRef.document(billId).delete().await()
    }

    suspend fun addItemToBillClientWithBillId(billId: String, item: Item) {
        val bill = billClientRef.document(billId).collection("Items")
        bill.add(item).addOnSuccessListener {
            bill.document(it.id).update(
                "itemId", it.id, "status", "open", "userId", userUid
            )
        }.await()
    }

    suspend fun updateItemToBillClientWithBillId(billId: String, item: Item) {
        billClientRef.document(billId).collection("Items")
            .document(item.itemId).set(item).await()
    }

    suspend fun deleteItemToBillClientWithBillId(billId: String, itemId: String) {
        billClientRef.document(billId).collection("Items")
            .document(itemId).delete().await()
    }


    suspend fun getItemsToBillClientWithBillId(billId: String): List<Item> {
        val items = billClientRef.document(billId).collection("Items")
            .get().await()
        return items!!.toObjects(Item::class.java)
    }

    suspend fun addPayBookToBill(billId: String, payBook: PayBook) {
        val bill = billClientRef.document(billId).collection("PayBook")
        bill.add(payBook).addOnSuccessListener {
            bill.document(it.id).update(
                "payBookId", it.id, "userId", userUid
            )
        }.await()
    }

    suspend fun updatePayBookToBillClientWithBillId(billId: String, payBook: PayBook) {
        billClientRef.document(billId).collection("PayBook")
            .document(payBook.payBookId).set(payBook).await()
    }

    suspend fun deletePayBookToBillClientWithBillId(billId: String, payBookId: String) {
        billClientRef.document(billId).collection("PayBook")
            .document(payBookId).delete().await()
    }


    suspend fun getPayBooksToBillClientWithBillId(billId: String): List<PayBook> {
        val payBooks = billClientRef.document(billId).collection("PayBook")
            .get().await()
        return payBooks!!.toObjects(PayBook::class.java)
    }
}