package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.theideal.data.model.Transfer
import kotlinx.coroutines.tasks.await

class FirebaseContactDeposit {
    private val db = Firebase.firestore
    private val transferRef = db.collection("Deposit")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun addDeposit(transfer: Transfer) {
        transferRef.add(transfer).addOnSuccessListener {
            transferRef.document(it.id).update(
                "transferId", it.id, "userId", currentUserUid, "contactId", transfer.contactId
            )
        }.await()
    }

    suspend fun updateDeposit(transfer: Transfer) {
        transferRef.document(transfer.transferId).set(transfer).await()
    }

    suspend fun deleteDeposit(transfer: Transfer) {
        transferRef.document(transfer.transferId).delete().await()
    }

    suspend fun getDepositsWithContactId(contactId: String): List<Transfer> {
        val deposit = transferRef
            .whereEqualTo("userId", currentUserUid)
            .whereEqualTo("contactId", contactId)
            .get().await()!!
        return deposit.toObjects(Transfer::class.java)
    }

    suspend fun getDepositsWithDepositId(depositId: String): List<Transfer> {
        val deposits = transferRef
            .whereEqualTo("userId", currentUserUid)
            .whereEqualTo("transferId", depositId)
            .get().await()!!
        return deposits.toObjects(Transfer::class.java)
    }
}



