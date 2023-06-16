package com.theideal.domain.repository

import com.theideal.data.model.Transfer
import com.theideal.data.remote.FirebaseContactDeposit

class DepositRepository(private val firebaseContactDeposit: FirebaseContactDeposit) {

    suspend fun addDeposit(transfer: Transfer) {
        firebaseContactDeposit.addDeposit(transfer)
    }

    suspend fun updateDeposit(transfer: Transfer) {
        firebaseContactDeposit.updateDeposit(transfer)
    }

    suspend fun deleteDeposit(transfer: Transfer) {
        firebaseContactDeposit.deleteDeposit(transfer)
    }

    suspend fun getDepositsWithContactId(contactId: String) =
        firebaseContactDeposit.getDepositsWithContactId(contactId)

    suspend fun getDepositWithDepositId(depositId: String) =
        firebaseContactDeposit.getDepositsWithDepositId(depositId)
}