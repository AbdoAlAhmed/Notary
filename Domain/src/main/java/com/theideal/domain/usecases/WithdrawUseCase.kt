package com.theideal.domain.usecases

import com.theideal.data.model.Transfer
import com.theideal.domain.repository.WithdrawRepository

class WithdrawUseCase(private val withdrawRepository: WithdrawRepository) {

    suspend fun addWithdraw(withdraw: Transfer) {
        withdrawRepository.addWithdraw(withdraw)
    }

    suspend fun updateWithdraw(withdraw: Transfer) {
        withdrawRepository.updateWithdraw(withdraw)
    }

    suspend fun deleteWithdraw(withdraw: Transfer) {
        withdrawRepository.deleteWithdraw(withdraw)
    }


    suspend fun getWithdrawsWithContactId(contactId: String) =
        withdrawRepository.getWithdrawsWithContactId(contactId)

    suspend fun getWithdrawWithWithdrawId(withdrawId: String) =
        withdrawRepository.getWithdrawWithWithdrawId(withdrawId)
}