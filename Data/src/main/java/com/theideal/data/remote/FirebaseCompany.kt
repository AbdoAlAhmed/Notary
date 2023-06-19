package com.theideal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.AdditionalTransactionsFees
import com.theideal.data.model.Company
import kotlinx.coroutines.tasks.await

class FirebaseCompany{
    private val db = FirebaseFirestore.getInstance()
    private val companyRef = db.collection("Company")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun createCompany(company: Company) {
        companyRef.add(company).addOnSuccessListener {
            companyRef.document(it.id).update(
                "companyId", it.id,
                "bossId", currentUserUid
            )
        }.await()
    }

    suspend fun getCompany(): Company? {
        return try {
            val querySnapshot = companyRef
                .whereEqualTo("bossId", currentUserUid)
                .get()
                .await()
            val companies = querySnapshot.toObjects(Company::class.java)
            companies.firstOrNull()
        } catch (e: Exception) {
            throw e
        }
    }


    fun updateCompany(company: Company) {
        companyRef.document(company.companyId).set(company)
    }

    fun deleteCompany(company: Company) {
        companyRef.document(company.companyId).delete()
    }

    suspend fun additionalTransactionToClient(
        companyId: String,
        additionalTransactionsFees: AdditionalTransactionsFees,
    ) {
        val additionalRef = companyRef.document(companyId)
        additionalRef.get().addOnSuccessListener {
            additionalRef.collection("AdditionalTransactionClient")
                .add(additionalTransactionsFees)
        }.await()

    }

    suspend fun additionalTransactionToSupplier(
        companyId: String,
        additionalTransactionsFees: AdditionalTransactionsFees,
    ) {
        val additionalRef = companyRef.document(companyId)
        additionalRef.get().addOnSuccessListener {
            additionalRef.collection("AdditionalTransactionSupplier")
                .add(additionalTransactionsFees)
        }.await()

    }

    fun deleteAdditionalTransactionToClient(company: Company) {
        companyRef.document(company.companyId).collection("AdditionalTransactionClient")
            .document(company.companyId).delete()

    }

    fun deleteAdditionalTransactionToSupplier(company: Company) {
        companyRef.document(company.companyId).collection("AdditionalTransactionSupplier")
            .document(company.companyId).delete()
    }

}