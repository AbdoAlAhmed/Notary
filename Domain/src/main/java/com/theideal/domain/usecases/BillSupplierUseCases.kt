package com.theideal.domain.usecases

import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.data.model.ItemInfo
import com.theideal.domain.repository.BillSupplierRepository

class BillSupplierUseCases(private val billRepository: BillSupplierRepository) {


    suspend fun createBill(billContact: BillContact, contact: Contact) {
        if (!billRepository.checkIfBillIsOpen(contact.contactId)) {
            billRepository.createBillSupplier(billContact, contact)
        }
    }

    suspend fun getBills(ContactId: String) =
        billRepository.getBillsSupplier(ContactId)

    suspend fun getLastBill(ContactId: String) =
        billRepository.getLastBillSupplier(ContactId)

    suspend fun updateBill(keyValue: Map<String, Any>, billId: String) {
        billRepository.updateBillSupplier(keyValue, billId)
    }

    suspend fun deleteBill(billContact: BillContact) {
        billRepository.deleteBillSupplier(billContact)
    }

    suspend fun addItemToBill(billContact: String, item: Item) {
        billRepository.addItemToBillClientFromSupplier(billContact, item)
    }

    suspend fun getItemsListBySupplierId(supplierId: String) =
        billRepository.getItemsListBySupplierId(supplierId)

    suspend fun calculateTheBillForSupplier(supplierId: String): List<Item> {
        val list: List<Item> = getItemsListBySupplierId(supplierId)
        val summedItems = list.groupBy { it.price }
            .map { (price, itemList) ->
                val totalWeight = itemList.sumOf { it.weight }
                val totalAmount = itemList.sumOf { it.amount }
                Item(price, totalWeight, totalAmount)
            }
        return summedItems
    }


    suspend fun addItemBillSupplierInfoWithBillId(billId: String, item: ItemInfo) {
        billRepository.addItemBillSupplierInfoWithBillId(billId, item)
    }

    suspend fun getListOfItemsBillInfo(billId: String) =
        billRepository.getListOfItemsBillInfo(billId)

    suspend fun getTotalAmountOfBill(billId: String): Double {
        val list: List<ItemInfo> = getListOfItemsBillInfo(billId)
        return list.toMutableList().sumOf { it.value }
    }

    suspend fun updateItemToBill(billContact: BillContact, item: Item) {
        billRepository.updateItemToBillSupplierWithBillId(billContact, item)
    }

    suspend fun deleteItemToBill(billContact: BillContact, item: Item) {
        billRepository.deleteItemToBillSupplierWithBillId(billContact, item)
    }


}