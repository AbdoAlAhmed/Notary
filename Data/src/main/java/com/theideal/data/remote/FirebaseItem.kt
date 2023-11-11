package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.Item
import kotlinx.coroutines.tasks.await

class FirebaseItem {
    private val db = FirebaseFirestore.getInstance()
    private val itemRef = db.collection("Item")
    private val userUid = FirebaseAuth.getInstance().currentUser!!.uid


    suspend fun addItemToBillClientWithBillId(billId: String, item: Item) {
        val bill = itemRef.add(item).addOnSuccessListener {
            itemRef.document(it.id).update(
                "itemId", it.id, "status", "open", "userId", userUid, "billId", billId
            )
        }.await()
    }

    suspend fun updateItemToBillClientWithBillId(item: Item) {
        itemRef.document(item.itemId).update(
            "price", item.price,
            "weight", item.weight,
            "amount", item.amount,
            "description", item.description,
            "status", item.status,
        ).await()
    }

    suspend fun deleteItemToBillClientWithItemId(itemId: String) {
        itemRef.document(itemId).delete().await()
    }

    suspend fun getItemsByBillId(billId: String): List<Item> {
        val items = itemRef.whereEqualTo("userId",userUid).whereEqualTo("billId", billId).get().await()
        return items.toObjects(Item::class.java)
    }

    suspend fun getItemsBySupplierId(supplierId: String): List<Item> {
        val items = itemRef.whereEqualTo("userId",userUid).whereEqualTo("supplierId", supplierId).get().await()
        return items.toObjects(Item::class.java)
    }

    suspend fun getItemByContactId(contactId: String): List<Item>{
        val items = itemRef.whereEqualTo("userId",userUid).whereEqualTo("contactId", contactId).get().await()
        return items.toObjects(Item::class.java)
    }

}