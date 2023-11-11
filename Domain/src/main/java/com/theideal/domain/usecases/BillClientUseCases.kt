package com.theideal.domain.usecases

import android.app.Application
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.data.model.PayBook
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.SupplierRepository

class BillClientUseCases(
    private val billRepository: BillClientRepository,
    private val billSupplierRepository: BillSupplierRepository,
    private val supplierRepository: SupplierRepository,
    private val transferUseCase: TransferUseCase,
    private val itemUseCase: ItemUseCase
) {


    private suspend fun calculateBills(contactId: String): Double {
        val list = getBillsByContactId(contactId)
        var remainingMoney = 0.0
        var amount = 0.0
        for (bill in list) {
            remainingMoney += bill.deptCalculate!!
            amount += bill.amount!!
        }
        return remainingMoney
    }

    suspend fun supplierTotal(contactId: String): Double {
        return transferUseCase.calculateTransfer(contactId) - calculateBills(contactId)
    }

    suspend fun clientTotal(contactId: String): Double {
        return calculateBills(contactId)
    }


    suspend fun checkIfBillIsOpen(contactId: String): Boolean {
        val list = billRepository.getBillsContact(contactId)
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

    suspend fun getBillsByContactId(contactId: String) = billRepository.getBillsContact(contactId)


    suspend fun getSuppliersNameFromId(): List<Pair<Contact, BillContact>> {
        return try {
            val listSupplier = mutableListOf<Contact>()
            val listOfBillContact = mutableListOf<BillContact>()
            var list = mutableListOf<Pair<Contact, BillContact>>()
            for (i in billSupplierRepository.getSuppliersIdIFBillOpen()) {
                listOfBillContact.add(i)
                listSupplier.addAll(supplierRepository.getSupplierWithId(i.contactId)!!)
                list = returnCombinedData(listSupplier, listOfBillContact)

            }

            list
        } catch (e: Exception) {
            emptyList()
        }

    }

    private fun returnCombinedData(
        supplier: List<Contact>, bill: List<BillContact>
    ): MutableList<Pair<Contact, BillContact>> {
        val list = mutableListOf<Pair<Contact, BillContact>>()
        for (i in supplier) {
            val billContact = bill.find { it.contactId == i.contactId }
            if (billContact != null) {
                list.add(Pair(i, billContact))
            }
        }
        return list

    }

    suspend fun updateBillSupplier(keyValue: Map<String, Double?>, billId: String) {
        billSupplierRepository.updateBillSupplier(keyValue, billId)
    }

    suspend fun getItemsFromBillId(billId: String): List<Item> {
        return itemUseCase.getItemsByBillId(billId)
    }

    suspend fun getTotalPaidMoney(billId: String): Double {
        val item = billRepository.getPayBooksFromBill(billId)
        var totalPaid = 0.0
        for (i in item) {
            totalPaid += i.amount
        }
        val updatePaidMoney = mapOf("paidMoney" to totalPaid)
        billRepository.updateBillClient(billId, updatePaidMoney)
//
        return totalPaid
    }

    suspend fun getTotalMoneyFromItem(billId: String): Double {
        val item = itemUseCase.getItemsByBillId(billId)
        var totalMoney = 0.0
        for (i in item) {
            totalMoney += i.money
        }
        return totalMoney
    }

    suspend fun setStatus(billId: String): String {
        val grossMoney = getTotalMoneyFromItem(billId)
        val totalPaidMoney = getTotalPaidMoney(billId)
        val otherFess = billRepository.getBillByBillId(billId).theBillOtherFees
        val totalMoney = grossMoney + otherFess!!
        val status = when {
            totalMoney <= totalPaidMoney -> "closed"
            totalMoney > totalPaidMoney -> "deferred"
            else -> "open"
        }
        val updateStatus = mapOf("status" to status)
        billRepository.updateBillClient(billId, updateStatus)
        return status
    }

    suspend fun totalMoneyItems(billId: String): Double {
        val items = itemUseCase.getItemsByBillId(billId)
        var total = 0.0
        for (i in items) {
            total += i.money
        }
        return total
    }

    suspend fun totalAmountItems(billId: String): Double {
        val items = itemUseCase.getItemsByBillId(billId)
        var total = 0.0
        for (i in items) {
            total += i.amount
        }
        return total
    }

    suspend fun updateItem(item: Item) {
        itemUseCase.updateItemToBillClientWithBillId(item)
    }

    suspend fun deleteItemFromBill(itemId: String) {
        itemUseCase.deleteItemToBillClientWithItemId(itemId)
    }

    suspend fun addItemToBillClientWithBillId(billId: String, item: Item) {
        itemUseCase.addItemToBillClientWithBillId(
            billId = billId, item = item
        )

    }


    suspend fun addPayBookToBill(billId: String, payBook: PayBook) {
        billRepository.addPayBookToBill(billId, payBook)
    }

    suspend fun deletePayBookFromBill(billId: String, payBookId: String) {
        billRepository.deletePayBookFromBill(billId, payBookId)
    }

    suspend fun getPayBooks(billId: String) = billRepository.getPayBooksFromBill(billId)


    suspend fun updatePayBook(billId: String, payBookId: String, payBook: Map<String, PayBook>) {
        billRepository.updatePayBookOnBill(billId, payBookId, payBook)
    }
}