package com.theideal.domain.usecases

import com.theideal.data.model.BillContact
import com.theideal.data.model.ItemWrapper
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.ClientRepository

class ClientsUseCases(
    private val clientRepository: ClientRepository,
    private val billClientRepo: BillClientRepository
) {


    private suspend fun getClientIds(): List<String> {
        val clients = clientRepository.getClientByUserId()
        return clients.map { it.contactId }
    }
    private suspend fun getBillContactMap(contactIds: List<String>): Map<String, List<BillContact>> {
        val billContactMap = mutableMapOf<String, List<BillContact>>()

        for (contactId in contactIds) {
            val billContacts = billClientRepo.getBillsContact(contactId)
            billContactMap[contactId] = billContacts
        }

        return billContactMap
    }
    suspend fun list(): List<ItemWrapper> {
        val contactIds = getClientIds()
        val billContactMap = getBillContactMap(contactIds)
        val clients = clientRepository.getClientByUserId()

        // Use flatMap to combine clients with their billContacts
        val itemWrappers = clients.flatMap { client ->
            val billContacts = billContactMap[client.contactId] ?: emptyList()
            billContacts.map { billContact ->
                ItemWrapper(billContact = billContact, contact = client)
            }
        }

        return itemWrappers
    }



}