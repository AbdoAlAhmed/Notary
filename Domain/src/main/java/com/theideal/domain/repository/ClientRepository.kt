package com.theideal.domain.repository

import com.theideal.data.model.Contact
import com.theideal.data.remote.FirebaseBillClient
import com.theideal.data.remote.FirebaseClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClientRepository(
    private val firebaseClient: FirebaseClient,
    private val firebaseBillClient: FirebaseBillClient
) {
    suspend fun createClient(client: Contact) = firebaseClient.createClient(client)


    suspend fun getClient(clientId: String) = firebaseClient.getClient(clientId)


    suspend fun getClientsByUserId() = firebaseClient.getClientsByUserId()



    suspend fun getBillsByUserId() = firebaseBillClient.getBillsByUserId()

    suspend fun getAllPayBook() = firebaseBillClient.getAllPayBook()


    suspend fun clientPhoneExists(phone: String) = firebaseClient.clientPhoneExists(phone)


    suspend fun updateClient(client: Contact, keyValue: Map<String, Any>) =
        firebaseClient.updateClient(client, keyValue)


    suspend fun deleteClient(client: Contact) = firebaseClient.deleteClient(client)

}