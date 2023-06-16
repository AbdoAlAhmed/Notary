package com.theideal.data.remote

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import kotlinx.coroutines.tasks.await

class FirebaseBillSupplier {
    private val db = FirebaseFirestore.getInstance()
    private val billSupplierRef = db.collection("BillSupplier")
    private val userUid = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun checkIfBillOpen(contactId: String): Boolean {
        val bill = billSupplierRef
            .whereEqualTo("userId", userUid)
            .whereEqualTo("contactId", contactId)
            .get().await()
        return bill!!.toObjects(BillContact::class.java).any { it.status == "open" }
    }

    suspend fun createBillSupplier(billSupplier: BillContact, contact: Contact) {
        billSupplierRef.add(billSupplier).addOnSuccessListener {
            billSupplierRef.document(it.id)
                .update(
                    "billId", it.id,
                    "userId", userUid,
                    "contactId", contact.phone,
                    "status", "open"
                )
        }.await()
    }

    suspend fun getBillSupplier(contactId: String): List<BillContact> {
        val bills = billSupplierRef.whereEqualTo("contactId", contactId).get().await()
        return bills.toObjects(BillContact::class.java)
    }

    suspend fun getLastBillSupplier(contactId: String): BillContact {
        val bills = billSupplierRef.whereEqualTo("contactId", contactId).get().await()
        return bills.toObjects(BillContact::class.java).last()
    }

    suspend fun updateBillSupplier(keyValue: Map<String, Any>, billId: String) {
        for ((key, value) in keyValue) {
            if (key == "amount") {
                billSupplierRef.document(billId).update(
                    key, FieldValue.increment(value as Double)
                ).await()
            } else {
                billSupplierRef.document(billId).update(
                    key, value
                ).await()
            }
        }
    }

    suspend fun deleteBillSupplier(billSupplier: BillContact) {
        billSupplierRef.document(billSupplier.billId).delete()
    }


    suspend fun addItemToBillSupplierWithBillId(billId: String, item: Item) {
        val bill = billSupplierRef.document(billId).collection("Items") //bill
        bill.add(item).addOnSuccessListener {
            bill.document(it.id).update(
                "itemId", it.id,
                "status", "open"
            )
        }.await()
    }

    suspend fun updateItemToBillSupplierWithBillId(billId: BillContact, item: Item) {
        billSupplierRef.document(billId.billId).collection("Items")
            .document(item.itemId).set(item)
    }

    suspend fun deleteItemToBillSupplierWithBillId(billId: BillContact, item: Item) {
        billSupplierRef.document(billId.billId).collection("Items")
            .document(item.itemId).delete()
    }

    suspend fun getSupplierIdIFBillOpen(): List<String> {
        val suppliersId =
            billSupplierRef.whereEqualTo("userId", userUid).whereEqualTo("status", "open").get()
                .await()
        return suppliersId.toObjects(BillContact::class.java).map { it.contactId }
    }
}