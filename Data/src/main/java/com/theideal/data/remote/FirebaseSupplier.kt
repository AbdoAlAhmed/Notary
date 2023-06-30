package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.Contact
import kotlinx.coroutines.tasks.await

class FirebaseSupplier {
    private val db = FirebaseFirestore.getInstance()
    private val supplierRef = db.collection("Supplier")
    private val userUid = FirebaseAuth.getInstance().currentUser!!.uid


    suspend fun createSupplier(contact: Contact) {
        supplierRef.add(contact).addOnSuccessListener {
            supplierRef.document(it.id).update(
                "contactId", it.id, "userId", userUid
            )
        }.await()
    }

    suspend fun supplierExists(contactId: String): Boolean {
        val supplier =
            supplierRef.whereEqualTo("userId", userUid).whereEqualTo("contactId", contactId).get()
                .await()
        return supplier.documents.isEmpty()
    }

    suspend fun getSuppliers(): List<Contact> {
        val suppliers = supplierRef.whereEqualTo("userId", userUid).get().await()
        return suppliers.toObjects(Contact::class.java)
    }

    suspend fun getSuppliersWithId(contactId: String): List<Contact> {
        val suppliers = supplierRef.whereEqualTo("userId", userUid)
            .whereEqualTo("contactId", contactId).get().await()
        return suppliers.toObjects(Contact::class.java)
    }


    suspend fun updateSupplier(supplier: Contact) {
        supplierRef.document(supplier.contactId).set(supplier).await()
    }

    suspend fun deleteSupplier(supplier: Contact) {
        supplierRef.document(supplier.contactId).delete().await()
    }
}