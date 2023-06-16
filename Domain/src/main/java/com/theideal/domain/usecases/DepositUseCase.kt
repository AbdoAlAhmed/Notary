package com.theideal.domain.usecases

import com.theideal.data.model.Transfer
import com.theideal.domain.repository.DepositRepository

class DepositUseCase(private val depositRepository: DepositRepository) {

    suspend fun addDeposit(transfer: Transfer) {
        depositRepository.addDeposit(transfer)
    }

    suspend fun updateDeposit(transfer: Transfer) {
        depositRepository.updateDeposit(transfer)
    }

    suspend fun deleteDeposit(transfer: Transfer) {
        depositRepository.deleteDeposit(transfer)
    }

    suspend fun getDepositsWithContactId(contactId: String) =
        depositRepository.getDepositsWithContactId(contactId)

    suspend fun getDepositWithDepositId(depositId: String) =
        depositRepository.getDepositWithDepositId(depositId)
}