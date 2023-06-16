package com.theideal.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.Contact

class FirebasePartner {
    private val db = FirebaseFirestore.getInstance()
    private val partnerRef = db.collection("Partner")

    fun createPartner(partner: Contact) {
        partnerRef.add(partner)
    }

    fun getPartner(partnerId: Contact) {
        partnerRef.document(partnerId.contactId).get()
    }

    fun updatePartner(partner: Contact) {
        partnerRef.document(partner.contactId).set(partner)
    }

    fun deletePartner(partner: Contact) {
        partnerRef.document(partner.contactId).delete()
    }
}