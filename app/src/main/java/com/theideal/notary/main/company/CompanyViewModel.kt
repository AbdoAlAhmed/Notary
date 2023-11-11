package com.theideal.notary.main.company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Company
import com.theideal.domain.repository.CompanyRepository
import com.theideal.domain.repository.UserRepository
import kotlinx.coroutines.launch

open class CompanyViewModel(
    private val companyRepo: CompanyRepository,
    private val userRepo: UserRepository,
) : ViewModel() {

    private val _navToClientTransactionsFees = MutableLiveData<Boolean>()
    val navToClientTransactionsFees: MutableLiveData<Boolean>
        get() = _navToClientTransactionsFees

    private val _navToWorkWithCompany = MutableLiveData<Boolean>()
    val navToWithCompany: MutableLiveData<Boolean>
        get() = _navToWorkWithCompany

    private val _companyComplete = MutableLiveData<Boolean>()
    val companyComplete: MutableLiveData<Boolean>
        get() = _companyComplete


    fun createCompany(company: Company) {
        viewModelScope.launch {
            companyRepo.createCompany(company)
            updateUserInfo()
        }
    }

    fun companyCompleteComplete() {
        _companyComplete.value = false
    }


    private fun updateUserInfo() {
        viewModelScope.launch {
            val company = companyRepo.getCompany()
            userRepo.updateUserInfo("companyId", company!!.companyId)
//            _navToClientTransactionsFees.postValue(true)
            _companyComplete.value = true
        }
    }
    // todo after create company update the company userId in user info

    fun navToClientTransactionsFeesComplete() {
        _navToClientTransactionsFees.value = false
    }

    fun workWithCompany() {
        _navToWorkWithCompany.value = true
    }

    fun naveToWorkWithCompanyComplete() {
        _navToWorkWithCompany.value = false
    }
}