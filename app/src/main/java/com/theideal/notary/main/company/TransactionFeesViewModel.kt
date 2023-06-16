package com.theideal.notary.main.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.AdditionalTransactionsFees
import com.theideal.domain.repository.CompanyRepository
import com.theideal.domain.repository.UserRepository
import kotlinx.coroutines.launch

class TransactionFeesViewModel(
    private val companyRepo: CompanyRepository,
    userRepo: UserRepository,
) :
    CompanyViewModel(companyRepo, userRepo) {
    private val _navToSupplierTransactionsFees = MutableLiveData<Boolean>()
    val navToSupplierTransactionsFees: LiveData<Boolean>
        get() = _navToSupplierTransactionsFees

    private val _completeCompanyInfo = MutableLiveData<Boolean>()
    val completeCompanyInfo: LiveData<Boolean>
        get() = _completeCompanyInfo



    private suspend fun getCompany() = companyRepo.getCompany()
    fun uploadClientTransactionFees(
        additionalTransactionsFees: AdditionalTransactionsFees,
    ) {
        viewModelScope.launch {
            companyRepo.additionalTransactionToClient(
                getCompany()!!.companyId,
                additionalTransactionsFees
            )
            _navToSupplierTransactionsFees.postValue(true)
        }

    }

    fun uploadSupplierTransactionFees(
        additionalTransactionsFees: AdditionalTransactionsFees,
    ) {
        viewModelScope.launch {
            companyRepo.additionalTransactionToSupplier(
                getCompany()!!.companyId,
                additionalTransactionsFees
            )
            _completeCompanyInfo.postValue(true)
        }
    }

    fun navToSupplierTransactionsFeesComplete() {
        _navToSupplierTransactionsFees.value = false
    }

    fun completeCompanyInfoComplete() {
        _completeCompanyInfo.value = false
    }
}