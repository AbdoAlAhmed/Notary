package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import kotlinx.coroutines.tasks.await

class FirebaseSupplier {
    private val db = FirebaseFirestore.getInstance()
    private val supplierRef = db.collection("Supplier")
    private val userUid = FirebaseAuth.getInstance().currentUser!!.uid


    suspend fun createSupplier(supplier: Contact) {
        supplierRef.add(supplier).addOnSuccessListener {
            supplierRef.document(it.id).update(
                "contactId", supplier.phone, "userId", userUid
            )
        }.await()
    }

    suspend fun supplierExists(supplierId: String): Boolean {
        val supplier =
            supplierRef.whereEqualTo("userId", userUid).whereEqualTo("contactId", supplierId).get()
                .await()
        return supplier.documents.isEmpty()
    }

    suspend fun getSuppliers(): List<Contact> {
        val suppliers = supplierRef.whereEqualTo("userId", userUid).get().await()
        return suppliers.toObjects(Contact::class.java)
    }

    suspend fun getSuppliersWithId(supplierId: String): List<Contact> {
        val suppliers = supplierRef.whereEqualTo("userId", userUid)
            .whereEqualTo("contactId", supplierId).get().await()
        return suppliers.toObjects(Contact::class.java)
    }


    suspend fun updateSupplier(supplier: Contact) {
        supplierRef.document(supplier.contactId).set(supplier).await()
    }

    suspend fun deleteSupplier(supplier: Contact) {
        supplierRef.document(supplier.contactId).delete().await()
    }
}