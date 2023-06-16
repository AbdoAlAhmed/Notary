package com.theideal.domain.repository

import com.theideal.data.model.Contact
import com.theideal.data.remote.FirebaseClient

class ClientRepository(private val firebaseClient: FirebaseClient) {
    suspend fun createClient(client: Contact) {
        firebaseClient.createClient(client)
    }

    suspend fun getClient(clientId: String) =
        firebaseClient.getClient(clientId)


    suspend fun getClientByUserId() = firebaseClient.getClientByUserId()


    suspend fun clientPhoneExists(phone: String) = firebaseClient.clientPhoneExists(phone)


    suspend fun updateClient(client: Contact) = firebaseClient.updateClient(client)


    suspend fun deleteClient(client: Contact) = firebaseClient.deleteClient(client)

}