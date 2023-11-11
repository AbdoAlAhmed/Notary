package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.BillContact
import com.theideal.data.model.PayBook
import kotlinx.coroutines.tasks.await

class FirebaseBillClient {
    private val db = FirebaseFirestore.getInstance()
    private val billClientRef = db.collection("BillClient")
    private val userUid = FirebaseAuth.getInstance().currentUser?.uid



    suspend fun createBillClient(contactId: String): BillContact {
        var billId = ""
        billClientRef.add(BillContact()).addOnSuccessListener {
            billClientRef.document(it.id).update(
                "billId", it.id, "userId", userUid, "contactId", contactId, "status", "open"
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
            billClientRef.whereEqualTo("userId", userUid).whereEqualTo("contactId", contactId).get()
                .await()
        return bill!!.toObjects(BillContact::class.java)
    }

    suspend fun getBillsByUserId(): List<BillContact> {
        val bill = billClientRef.whereEqualTo("userId", userUid).get().await()
        return bill!!.toObjects(BillContact::class.java)
    }

    suspend fun getBillByBillId(billId: String): BillContact {
        val bill =
            billClientRef.whereEqualTo("userId", userUid).whereEqualTo("billId", billId).get()
                .await()
        return bill!!.toObjects(BillContact::class.java)[0]
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

    suspend fun deleteAllBillClient(contactId: String) {
        val bills =
            billClientRef.whereEqualTo("userId", userUid).whereEqualTo("contactId", contactId).get()
                .await()
        bills.documents.forEach {
            it.reference.delete().await()
        }
    }

    suspend fun addPayBookToBill(billId: String, payBook: PayBook) {
        val bill = billClientRef.document(billId).collection("PayBook")
        bill.add(payBook).addOnSuccessListener {
            bill.document(it.id).update(
                "payBookId", it.id, "userId", userUid
            )
        }.await()
    }

    suspend fun getAllPayBook(): List<PayBook> {
        val payBooks = db.collectionGroup("PayBook").whereEqualTo("userId", userUid)
            .whereNotEqualTo("payBookId", "").get().await()
        return payBooks.toObjects(PayBook::class.java)
    }

    suspend fun updatePayBookToBillClientWithBillId(
        billId: String, payBookId: String, keyValue: Map<String, Any>
    ) {
        for ((key, value) in keyValue) {
            billClientRef.document(billId).collection("PayBook").document(payBookId).update(
                key, value
            ).await()
        }
    }

    suspend fun deletePayBookToBillClientWithBillId(billId: String, payBookId: String) {
        billClientRef.document(billId).collection("PayBook").document(payBookId).delete().await()
    }


    suspend fun getPayBooksToBillClientWithBillId(billId: String): List<PayBook> {
        val payBooks = billClientRef.document(billId).collection("PayBook").get().await()
        return payBooks!!.toObjects(PayBook::class.java)
    }
}