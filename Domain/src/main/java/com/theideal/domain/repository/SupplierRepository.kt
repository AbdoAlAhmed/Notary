package com.theideal.domain.repository

import com.theideal.data.model.Contact
import com.theideal.data.remote.FirebaseSupplier

class SupplierRepository(private val db: FirebaseSupplier) {


    suspend fun createSupplier(supplier: Contact) {
        db.createSupplier(supplier)
    }

    suspend fun getSuppliers() = db.getSuppliers()

    suspend fun getSupplierWithId(supplierId: String) = db.getSuppliersWithId(supplierId)


    suspend fun supplierExists(supplierId: String) = db.supplierExists(supplierId)




    suspend fun updateSupplier(supplier: Contact) {
        db.updateSupplier(supplier)
    }

    suspend fun deleteSupplier(supplier: Contact) {
        db.deleteSupplier(supplier)
    }
}