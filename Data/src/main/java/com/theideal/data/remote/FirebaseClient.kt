package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.Contact
import kotlinx.coroutines.tasks.await

class FirebaseClient : FirebaseAuthentication() {
    private val db = FirebaseFirestore.getInstance()
    private val clientRef = db.collection("Client")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun createClient(client: Contact) {
        clientRef.add(client).addOnSuccessListener {
            clientRef.document(it.id).update(
                "contactId", client.phone, "userId", currentUser?.uid
            )
        }.await()

    }

    suspend fun getClient(clientId: String): Contact? {
        val contactInfo = clientRef.whereEqualTo("userId", currentUserUid)
            .whereEqualTo("contactId", clientId).get().await()
        val contacts = contactInfo.toObjects(Contact::class.java)
        return contacts.firstOrNull()
    }


    suspend fun getClientByUserId(): List<Contact> {
        val contactInfo = clientRef.whereEqualTo("userId", currentUser!!.uid).get().await()
        return contactInfo.toObjects(Contact::class.java)
    }

    suspend fun clientPhoneExists(phone: String): Boolean {
        val contactInfo = clientRef
            .whereEqualTo("userId", currentUser!!.uid)
            .whereEqualTo(
                "contactId", phone
            ).get().await()
        return contactInfo.documents.isEmpty()
    }

    suspend fun updateClient(client: Contact) {
        clientRef.whereEqualTo("userId", currentUser!!.uid)
            .whereEqualTo("contactId", client.contactId)
            .get().addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    clientRef.document(it.documents[0].id).set(client)
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }.await()
    }

    suspend fun deleteClient(client: Contact) {
        clientRef.whereEqualTo("userId", currentUser!!.uid)
            .whereEqualTo("contactId", client.contactId)
            .get().addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    clientRef.document(it.documents[0].id).delete()
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }.await()
    }

}