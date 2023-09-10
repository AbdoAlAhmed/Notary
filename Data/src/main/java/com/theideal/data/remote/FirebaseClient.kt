package com.theideal.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.Contact
import kotlinx.coroutines.tasks.await

class FirebaseClient {
    private val db = FirebaseFirestore.getInstance()
    private val clientRef = db.collection("Client")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun createClient(contact: Contact): Contact {
        val clientRef = clientRef.add(contact).addOnSuccessListener {
            clientRef.document(it.id).update(
                "contactId", it.id, "userId", currentUserUid
            )
        }.await()

        return Contact().copy(contactId = clientRef.id)

    }

    suspend fun getClient(contactId: String): Contact? {
        val contactInfo = clientRef.whereEqualTo("userId", currentUserUid)
            .whereEqualTo("contactId", contactId).get().await()
        val contacts = contactInfo.toObjects(Contact::class.java)
        return contacts.firstOrNull()
    }


    suspend fun getClientsByUserId(): List<Contact> {
        val contactInfo = clientRef.whereEqualTo("userId", currentUserUid).get().await()
        return contactInfo.toObjects(Contact::class.java)
    }

    suspend fun clientPhoneExists(phone: String): Boolean {
        val contactInfo = clientRef
            .whereEqualTo("userId", currentUserUid)
            .whereEqualTo(
                "phone", phone
            ).get().await()
        return contactInfo.documents.isEmpty()
    }

    suspend fun updateClient(contact: Contact) {
        clientRef.whereEqualTo("userId", currentUserUid)
            .whereEqualTo("contactId", contact.contactId)
            .get().addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    clientRef.document(it.documents[0].id).set(contact)
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }.await()
    }

    suspend fun deleteClient(contact: Contact) {
        clientRef.document(contact.contactId).delete().await()
    }

}