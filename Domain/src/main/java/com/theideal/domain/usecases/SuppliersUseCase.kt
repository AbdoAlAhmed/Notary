package com.theideal.domain.usecases

import com.theideal.data.model.Contact
import com.theideal.domain.repository.SupplierRepository

class SuppliersUseCase(private val supplierRepository: SupplierRepository) {

    suspend fun getSuppliers() = supplierRepository.getSuppliers()

    suspend fun getSupplierWithId(supplierId: String) =
        supplierRepository.getSupplierWithId(supplierId)

    suspend fun supplierExists(supplierId: String) = supplierRepository.supplierExists(supplierId)


    suspend fun createSupplier(supplier: Contact) {
        supplierRepository.createSupplier(supplier)
    }

    suspend fun updateSupplier(supplier: Contact, keyValue: Map<String, Any>) =
        supplierRepository.updateSupplier(supplier, keyValue)

    suspend fun supplierPhoneExist(phone: String) = supplierRepository.supplierPhoneExist(phone)

    suspend fun deleteSupplier(supplier: Contact) = supplierRepository.deleteSupplier(supplier)
}