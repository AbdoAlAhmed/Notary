package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.data.model.ItemInfo
import kotlinx.coroutines.tasks.await

class FirebaseBillSupplier {
    private val db = FirebaseFirestore.getInstance()
    private val billSupplierRef = db.collection("BillSupplier")
    private val billClientRef = db.collection("BillClient")
    private val itemRef = db.collection("Items")
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
                    "contactId", contact.contactId,
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
            billSupplierRef.document(billId).update(
                key, value
            ).await()

        }
    }

    suspend fun deleteBillSupplier(billSupplier: BillContact) {
        billSupplierRef.document(billSupplier.billId).delete()
    }

    suspend fun addInfoItemToBillSupplierWithBillId(billId: String, itemInfo: ItemInfo) {
        val bill = billSupplierRef.document(billId).collection("ItemsInfo") //bill
        bill.add(itemInfo).addOnSuccessListener {
            bill.document(it.id).update(
                "itemId", it.id,
                "userId", userUid
            )
        }.await()
    }


    suspend fun getListOfItemBillInfo(billId: String): List<ItemInfo> {
        val itemsInfo = billSupplierRef.document(billId).collection("ItemsInfo").get().await()
        return itemsInfo.toObjects(ItemInfo::class.java)
    }


    suspend fun addItemToBillClientFromSupplier(billId: String, item: Item) {
        itemRef.add(item).addOnSuccessListener {
            itemRef.document(it.id).update(
                "itemId", it.id,
                "userId", userUid, "billId", billId

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

    // todo i'm here it's not returning the list
    suspend fun getItemListBySupplierId(supplierId: String): List<Item> {
        val items = itemRef
            .whereEqualTo("supplierId", supplierId)
            .get().await()

        return items.toObjects(Item::class.java)
    }


    // todo change it to close when the amount is finished
    // and instead of return just the contact id you should return the all data class ( contact)
    suspend fun getSupplierIdIFBillOpen(): List<String> {
        val suppliersId =
            billSupplierRef.whereEqualTo("userId", userUid).whereEqualTo("status", "open").get()
                .await()
        return suppliersId.toObjects(BillContact::class.java).map { it.contactId }
    }
}