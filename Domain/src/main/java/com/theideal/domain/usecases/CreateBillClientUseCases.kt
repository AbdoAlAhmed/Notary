package com.theideal.domain.usecases

import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.SupplierRepository

class CreateBillClientUseCases(
    private val billRepository: BillClientRepository,
    private val billSupplierRepository: BillSupplierRepository,
    private val supplierRepository: SupplierRepository,
) {


    suspend fun checkIfBillIsOpen(contactId: String): Boolean {
        val list = billRepository.checkIfBillIsOpen(contactId)
        var isOpen = false
        for (bill in list) {
            if (bill.status == "open") {
                isOpen = true
                break
            }
        }
        return isOpen
    }


    suspend fun createBill(contactId: String): BillContact {
        return billRepository.createBillClient(contactId)
    }

    suspend fun deleteBill(billId: String) {
        billRepository.deleteBillClient(billId)
    }

    suspend fun updateBill(billId: String, KeyValue: Map<String, Any>) {
        billRepository.updateBillClient(billId = billId, keyValue = KeyValue)
    }

    suspend fun getBillsByContactId(contactId: String) =
        billRepository.getBillsContact(contactId)

    suspend fun getSuppliersNameFromId(): List<Contact> {
        return try {
            val list = mutableListOf<Contact>()
            for (i in billSupplierRepository.getSuppliersIdIFBillOpen()) {
                list.addAll(supplierRepository.getSupplierWithId(i))
            }
            list
        } catch (e: Exception) {
            emptyList()
        }

    }

    suspend fun getItemsFromBillId(billId: String): List<Item> {
        return billRepository.getItemsByBillId(billId)
    }

    suspend fun totalMoneyItems(billId: String): Double {
        val items = billRepository.getItemsByBillId(billId)
        var total = 0.0
        for (i in items) {
            total += i.money
        }
        return total
    }

    suspend fun totalAmountItems(billId: String): Double {
        val items = billRepository.getItemsByBillId(billId)
        var total = 0.0
        for (i in items) {
            total += i.amount
        }
        return total
    }

    suspend fun updateItem(billId: String, item: Item) {
        billRepository.updateItemToBillClientWithBillId(billId, item)
    }

    suspend fun deleteItemFromBill(billId: String, itemId: String) {
        billRepository.deleteItemToBillClientWithBillId(billId, itemId)
    }

    suspend fun addItemToBillClientWithBillId(billId: String, item: Item) {
        billRepository.addItemToBillClientWithBillId(
            billId = billId,
            item = item
        )

        val updateBillInfo = mapOf(
            "amount" to totalAmountItems(billId),
            "grossMoney" to totalMoneyItems(billId)
        )

        billRepository.updateBillClient(
            billId = billId,
            keyValue = updateBillInfo
        )

    }


}