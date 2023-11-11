package com.theideal.domain.repository

import com.theideal.data.model.Item
import com.theideal.data.remote.FirebaseItem

class ItemRepository(private val firebaseItem: FirebaseItem) {

    suspend fun addItemToBillClientWithBillId(billId: String, item: Item) {
        firebaseItem.addItemToBillClientWithBillId(billId, item)
    }

    suspend fun updateItemToBillClientWithBillId(item: Item) {
        firebaseItem.updateItemToBillClientWithBillId(item)
    }

    suspend fun deleteItemToBillClientWithItemId(itemId: String) {
        firebaseItem.deleteItemToBillClientWithItemId(itemId)
    }

    suspend fun getItemsByBillId(billId: String): List<Item> {
        return firebaseItem.getItemsByBillId(billId)
    }

    suspend fun getItemsBySupplierId(supplierId: String): List<Item> {
        return firebaseItem.getItemsBySupplierId(supplierId)
    }

    suspend fun getItemByContactId(contactId: String): List<Item>{
        return firebaseItem.getItemByContactId(contactId)
    }
}