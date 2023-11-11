package com.theideal.domain.usecases

import com.theideal.data.model.Item
import com.theideal.domain.repository.ItemRepository

class ItemUseCase(private val itemRepository: ItemRepository) {
    suspend fun addItemToBillClientWithBillId(billId: String, item: Item) {
        itemRepository.addItemToBillClientWithBillId(billId, item)
    }

    suspend fun updateItemToBillClientWithBillId(item: Item) {
        itemRepository.updateItemToBillClientWithBillId(item)
    }

    suspend fun deleteItemToBillClientWithItemId(itemId: String) {
        itemRepository.deleteItemToBillClientWithItemId(itemId)
    }

    suspend fun getItemsByBillId(billId: String): List<Item> {
        return itemRepository.getItemsByBillId(billId)
    }

    suspend fun getItemsBySupplierId(supplierId: String): List<Item> {
        return itemRepository.getItemsBySupplierId(supplierId)
    }

    suspend fun getItemByContactId(contactId: String): List<Item>{
        return itemRepository.getItemByContactId(contactId)
    }
}