package com.theideal.domain.usecases

import com.theideal.data.model.Contact
import com.theideal.domain.repository.SupplierRepository

class SupplierUseCase(private val supplierRepository: SupplierRepository) {

    suspend fun getSuppliers() = supplierRepository.getSuppliers()

    suspend fun getSupplierWithId(supplierId: String) = supplierRepository.getSupplierWithId(supplierId)
    suspend fun supplierExists(supplierId: String) = supplierRepository.supplierExists(supplierId)


    suspend fun createSupplier(supplier: Contact) { supplierRepository.createSupplier(supplier) }

    suspend fun updateSupplier(supplier: Contact) = supplierRepository.updateSupplier(supplier)

    suspend fun deleteSupplier(supplier: Contact) = supplierRepository.deleteSupplier(supplier)
}