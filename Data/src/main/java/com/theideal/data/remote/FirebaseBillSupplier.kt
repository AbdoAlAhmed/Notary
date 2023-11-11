package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.BillContact
import com.theideal.data.model.ItemInfo
import kotlinx.coroutines.tasks.await

class FirebaseBillSupplier {
    private val db = FirebaseFirestore.getInstance()
    private val billSupplierRef = db.collection("BillSupplier")
    private val userUid = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun checkIfBillOpen(contactId: String): List<BillContact> {
        val bill =
            billSupplierRef.whereEqualTo("userId", userUid).whereEqualTo("contactId", contactId)
                .get().await()
        return bill!!.toObjects(BillContact::class.java)
    }

    suspend fun createBillSupplier(contactId: String): BillContact {
        var billId = ""
        billSupplierRef.add(BillContact()).addOnSuccessListener {
            billSupplierRef.document(it.id).update(
                "billId", it.id, "userId", userUid, "contactId", contactId, "status", "open"
            )
            billId = it.id
        }.await()
        return BillContact(
            billId,
            userUid,
            contactId,
            "open",
        )
    }

    suspend fun getBillSupplier(contactId: String): List<BillContact> {
        val bills =
            billSupplierRef.whereEqualTo("contactId", contactId).whereEqualTo("userId", userUid)
                .get().await()
        return bills.toObjects(BillContact::class.java)
    }


    suspend fun updateBillSupplier(keyValue: Map<String, Double?>, billId: String) {
        for ((key, value) in keyValue) {
            billSupplierRef.document(billId).update(
                key, value
            ).await()
        }
    }

    suspend fun deleteBillSupplier(billSupplier: BillContact) {
        billSupplierRef.document(billSupplier.billId).delete()
    }


    suspend fun getListOfItemBillInfo(billId: String): List<ItemInfo> {
        val itemsInfo = billSupplierRef.document(billId).collection("ItemsInfo").get().await()
        return itemsInfo.toObjects(ItemInfo::class.java)
    }


    suspend fun getSupplierIFBillOpen(): List<BillContact> {
        val suppliersId =
            billSupplierRef.whereEqualTo("userId", userUid).whereEqualTo("status", "open").get()
                .await()
        return suppliersId.toObjects(BillContact::class.java)
    }
}