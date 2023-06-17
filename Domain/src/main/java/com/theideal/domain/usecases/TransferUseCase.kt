package com.theideal.domain.usecases

import com.theideal.data.model.Transfer

class TransferUseCase(
    private val depositUseCase: DepositUseCase,
    private val withdrawUseCase: WithdrawUseCase
) {

    suspend fun getListOfTransfer(contactId: String): List<Transfer> {
        val listOfTransfer = mutableListOf<Transfer>()
        listOfTransfer.addAll(depositUseCase.getDepositsWithContactId(contactId))
        listOfTransfer.addAll(withdrawUseCase.getWithdrawsWithContactId(contactId))
        return listOfTransfer
    }

    suspend fun calculateTransfer(contactId: String): Double {
        val listDeposit = depositUseCase.getDepositsWithContactId(contactId)
        val listWithdraw = withdrawUseCase.getWithdrawsWithContactId(contactId)
        var totalDeposit = 0.0
        var totalWithdraw = 0.0
        for (i in listDeposit) {
            totalDeposit += i.amount
        }
        for (i in listWithdraw) {
            totalWithdraw += i.amount
        }
        return totalDeposit - totalWithdraw
    }

    suspend fun addDeposit(transfer: Transfer) {
        depositUseCase.addDeposit(transfer)
    }

    suspend fun updateDeposit(transfer: Transfer) {
        depositUseCase.updateDeposit(transfer)
    }

    suspend fun deleteDeposit(transfer: Transfer) {
        depositUseCase.deleteDeposit(transfer)
    }

    suspend fun getDepositsWithContactId(contactId: String) =
        depositUseCase.getDepositsWithContactId(contactId)

    suspend fun getDepositWithDepositId(depositId: String) =
        depositUseCase.getDepositWithDepositId(depositId)

    suspend fun addWithdraw(withdraw: Transfer) {
        withdrawUseCase.addWithdraw(withdraw)
    }

    suspend fun updateWithdraw(withdraw: Transfer) {
        withdrawUseCase.updateWithdraw(withdraw)
    }

    suspend fun deleteWithdraw(withdraw: Transfer) {
        withdrawUseCase.deleteWithdraw(withdraw)
    }

    suspend fun getWithdrawsWithContactId(contactId: String) =
        withdrawUseCase.getWithdrawsWithContactId(contactId)

    suspend fun getWithdrawWithWithdrawId(withdrawId: String) =
        withdrawUseCase.getWithdrawWithWithdrawId(withdrawId)
}