package com.theideal.notary.di

import com.theideal.data.remote.FirebaseBillSupplier
import com.theideal.data.remote.FirebaseClient
import com.theideal.data.remote.FirebaseSupplier
import com.theideal.data.remote.FirebaseUser
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.repository.SupplierRepository
import com.theideal.domain.repository.UserRepository
import com.theideal.domain.usecases.CreateBillSupplierUseCases
import com.theideal.domain.usecases.SupplierUseCase
import com.theideal.notary.main.supplier.SupplierViewModel
import com.theideal.notary.main.supplier.theSupplier.CreateSupplierViewModel
import com.theideal.notary.main.supplier.theSupplier.TheSupplierBillViewModel
import com.theideal.notary.main.supplier.theSupplier.TheSupplierViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val supplier = module {
    single {
        FirebaseSupplier()
    }
    single {
        SupplierRepository(get())
    }
    single {
        SupplierUseCase(get())
    }
    single {
        FirebaseClient()
    }
    single {
        ClientRepository(get())
    }
    single {
        SupplierUseCase(get())
    }
    single {
        FirebaseUser()
    }
    single {
        UserRepository(get())
    }
    single {
        SupplierUseCase(get())
    }
    viewModel {
        SupplierViewModel(get(), get())
    }
    viewModel {
        CreateSupplierViewModel(get())
    }
}

val createBillSupplier = module {
    single {
        FirebaseBillSupplier()
    }
    single {
        BillSupplierRepository(get())
    }
    single {
        CreateBillSupplierUseCases(get())
    }
    viewModel {
        TheSupplierBillViewModel(get())
    }
    viewModel {
        TheSupplierViewModel(get(), get(), get())
    }
}

val supplierModule = listOf(supplier, createBillSupplier)