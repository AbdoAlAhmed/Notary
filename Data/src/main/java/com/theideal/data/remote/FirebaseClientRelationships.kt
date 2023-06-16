package com.theideal.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.Contact

class FirebaseClientRelationships {
    private val db = FirebaseFirestore.getInstance()
    private val clientRelationshipsRef = db.collection("ClientRelationships")

    fun createClientRelationships(clientRelationships: Contact) {
        clientRelationshipsRef.add(clientRelationships)
    }

    fun getClientRelationships(clientRelationshipsId: Contact) {
        clientRelationshipsRef.document(clientRelationshipsId.contactId).get()
    }

    fun updateClientRelationships(clientRelationships: Contact) {
        clientRelationshipsRef.document(clientRelationships.contactId).set(clientRelationships)
    }

    fun deleteClientRelationships(clientRelationships: Contact) {
        clientRelationshipsRef.document(clientRelationships.contactId).delete()
    }
}