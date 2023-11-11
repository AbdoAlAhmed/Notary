package com.theideal.domain.usecases

import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.ClientRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ClientsUseCases(
    private val clientRepository: ClientRepository,
    private val billClientRepo: BillClientRepository,
    private val itemUseCase: ItemUseCase
) {


    suspend fun returnStatus(): String {
        val contactId = getClientsTodayByUserId()
        var status = ""
        for (i in contactId) {
            val bills = billClientRepo.getBillsContact(contactId = i.contactId)
            for (i in bills) {
                if (i.status == "open") {
                    status = "open"
                    break
                } else if (i.status == "deferred") {
                    status = "deferred"
                    break
                } else {
                    status = "closed"
                }
            }
        }
        return status
    }

    suspend fun getAmountFromItemByContactId(): Double {
        val contactId = getClientsTodayByUserId()
        var amount = 0.0
        for (i in contactId) {
            val items = itemUseCase.getItemByContactId(i.contactId)
            for (i in items) {
                amount += i.amount!!
            }
        }
        return amount
    }

    suspend fun getClientsByUserId() = clientRepository.getClientsByUserId()

    suspend fun getClient(clientId: String) = clientRepository.getClient(clientId = clientId)

    private suspend fun deleteAllBillsByContactId(contact: Contact) {
        billClientRepo.deleteAllBillClient(contact.contactId)
        clientRepository.deleteClient(contact)
    }

    suspend fun deleteClient(contact: Contact) = deleteAllBillsByContactId(contact)

    suspend fun clientPhoneExist(phone: String) = clientRepository.clientPhoneExists(phone)

    suspend fun updateClient(contact: Contact, keyValue: Map<String, Any>) =
        clientRepository.updateClient(contact, keyValue)

    suspend fun getClientsTodayByUserId(): List<Contact> {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
        return clientRepository.getClientsByUserId()
            .filter { dateFormat.format(it.createAt.toDate()) == dateFormat.format(currentDate) }
    }

    suspend fun getClientsToday(): List<Pair<Contact, BillContact>> {
        val clients = getClientsTodayByUserId()
        var status = returnStatus()
        var amount = getAmountFromItemByContactId()
        val billContactMap = BillContact(status = status, amount = amount)

        return combineClientAndBillContact(clients, billContactMap)
    }

    suspend fun getBillContactInfoByContactId(contactId: String): List<BillContact> {
        val listOfBillContact = mutableListOf<BillContact>()
        for (i in getClientsByUserId()) {
            listOfBillContact.addAll(billClientRepo.getBillsContact(i.contactId))
        }
        return listOfBillContact
    }

    fun combineClientAndBillContact(
        clients: List<Contact>, billContacts: BillContact
    ): List<Pair<Contact, BillContact>> {
        val list = mutableListOf<Pair<Contact, BillContact>>()
        for (i in clients) {
            list.add(Pair(i, billContacts))
        }
        return list
    }

    suspend fun getBillsByUserId() = clientRepository.getBillsByUserId()

    suspend fun getDeptMoney() =
        getBillsByUserId().sumOf { it.theBillOtherFees!! + it.grossMoney!! - it.paidMoney!! }


    suspend fun getAllPayBookToday(): Double {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yy", Locale.getDefault())

        return clientRepository.getAllPayBook()
            .filter { dateFormat.format(it.history.toDate()) == dateFormat.format(currentDate) }
            .sumByDouble { it.amount }
    }


}