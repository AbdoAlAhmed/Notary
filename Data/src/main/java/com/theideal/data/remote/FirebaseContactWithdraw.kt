package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.theideal.data.model.Transfer
import kotlinx.coroutines.tasks.await

class FirebaseContactWithdraw {
    private val db = Firebase.firestore
    private val withdrawRef = db.collection("Withdraw")
    private val userUid = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun addWithdraw(transfer: Transfer) {
        withdrawRef.add(transfer).addOnSuccessListener {
            withdrawRef.document(it.id).update(
                "transferId", it.id, "userId", userUid
            )
        }.await()
    }

    suspend fun updateWithdraw(transfer: Transfer) {
        withdrawRef.document(transfer.transferId).set(transfer).await()
    }

    suspend fun deleteWithdraw(transfer: Transfer) {
        withdrawRef.document(transfer.transferId).delete().await()
    }

    suspend fun getWithdrawsWithContactId(contactId: String): List<Transfer> {
        val withdraw =
            withdrawRef.whereEqualTo("userId", userUid)
                .whereEqualTo("contactId", contactId)
                .get().await()
        return withdraw.toObjects(Transfer::class.java)
    }

    suspend fun getWithdrawWithWithdrawId(transferId: String): Transfer {
        val withdraws = withdrawRef.whereEqualTo("transferId", transferId)
            .get().await()!!
        return withdraws.toObjects(Transfer::class.java).firstOrNull()!!
    }
}