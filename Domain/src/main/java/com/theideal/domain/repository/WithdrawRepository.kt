package com.theideal.domain.repository

import com.theideal.data.model.Transfer
import com.theideal.data.remote.FirebaseContactWithdraw

class WithdrawRepository(private val firebaseContactWithdraw: FirebaseContactWithdraw) {

    suspend fun addWithdraw(withdraw: Transfer) {
        firebaseContactWithdraw.addWithdraw(withdraw)
    }

    suspend fun updateWithdraw(withdraw: Transfer) {
        firebaseContactWithdraw.updateWithdraw(withdraw)
    }

    suspend fun deleteWithdraw(withdraw: Transfer) {
        firebaseContactWithdraw.deleteWithdraw(withdraw)
    }

    suspend fun getWithdrawsWithContactId(contactId: String) =
        firebaseContactWithdraw.getWithdrawsWithContactId(contactId)

    suspend fun getWithdrawWithWithdrawId(withdrawId: String) =
        firebaseContactWithdraw.getWithdrawWithWithdrawId(withdrawId)
}