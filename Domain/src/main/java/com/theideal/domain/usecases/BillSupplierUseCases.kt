package com.theideal.domain.usecases

import com.theideal.data.model.BillContact
import com.theideal.data.model.Item
import com.theideal.domain.repository.BillSupplierRepository

class BillSupplierUseCases(
    private val billRepository: BillSupplierRepository,
    private val itemUseCase: ItemUseCase
) {


    suspend fun createBill(contactId: String) =
        billRepository.createBillSupplier(contactId = contactId)

    suspend fun checkIfBillIsOpen(contactId: String) =
        billRepository.checkIfBillIsOpen(contactId)

    suspend fun getBills(ContactId: String) =
        billRepository.getBillsSupplier(ContactId)


    suspend fun updateBill(keyValue: Map<String, Double?>, billId: String) {
        billRepository.updateBillSupplier(keyValue, billId)
    }

    suspend fun deleteBill(billContact: BillContact) {
        billRepository.deleteBillSupplier(billContact)
    }

    suspend fun addItemToBill(billContact: String, item: Item) {
        itemUseCase.addItemToBillClientWithBillId(billContact, item)
    }

    suspend fun getItemsListBySupplierId(supplierId: String) =
        itemUseCase.getItemsBySupplierId(supplierId)

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


    suspend fun getTotalMoney(supplierId: String): Double {
        val items = getItemsListBySupplierId(supplierId = supplierId)
        return items.sumOf { it.money }
    }

}