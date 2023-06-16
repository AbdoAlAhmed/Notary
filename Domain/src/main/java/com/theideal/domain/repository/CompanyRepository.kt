package com.theideal.domain.repository

import com.theideal.data.model.AdditionalTransactionsFees
import com.theideal.data.model.Company
import com.theideal.data.remote.FirebaseCompany

class CompanyRepository(private val firebaseCompany: FirebaseCompany) {
    suspend fun createCompany(company: Company) {
        firebaseCompany.createCompany(company)
    }

    suspend fun getCompany(): Company?  {
        return firebaseCompany.getCompany()
    }

    fun updateCompany(company: Company) {
        firebaseCompany.updateCompany(company)
    }

    fun deleteCompany(company: Company) {
        firebaseCompany.deleteCompany(company)
    }

    suspend fun additionalTransactionToClient(
        companyId: String,
        additionalTransactionsFees: AdditionalTransactionsFees,
    ) {
        firebaseCompany.additionalTransactionToClient(companyId, additionalTransactionsFees)
    }

    suspend fun additionalTransactionToSupplier(
        companyId: String,
        additionalTransactionsFees: AdditionalTransactionsFees,
    ) {
        firebaseCompany.additionalTransactionToSupplier(companyId, additionalTransactionsFees)
    }

    fun deleteAdditionalTransactionToClient(company: Company) {
        firebaseCompany.deleteAdditionalTransactionToClient(company)
    }

    fun deleteAdditionalTransactionToSupplier(company: Company) {
        firebaseCompany.deleteAdditionalTransactionToSupplier(company)
    }

}