package com.theideal.notary.di

import com.theideal.data.remote.FirebaseBillClient
import com.theideal.data.remote.FirebaseBillSupplier
import com.theideal.data.remote.FirebaseClient
import com.theideal.data.remote.FirebaseItem
import com.theideal.data.remote.FirebaseSupplier
import com.theideal.data.remote.FirebaseUser
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.repository.ItemRepository
import com.theideal.domain.repository.SupplierRepository
import com.theideal.domain.repository.UserRepository
import com.theideal.domain.usecases.BillSupplierUseCases
import com.theideal.domain.usecases.ItemUseCase
import com.theideal.domain.usecases.SuppliersUseCase
import com.theideal.notary.main.supplier.SupplierViewModel
import com.theideal.notary.main.supplier.theSupplier.CreateSupplierViewModel
import com.theideal.notary.main.supplier.theSupplier.SupplierAndTheSupplierViewModel
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
        SuppliersUseCase(get())
    }
    single {
        FirebaseClient()
    }
    single {
        FirebaseBillClient()
    }
    single {
        ClientRepository(get(), get())
    }
    single {
        SuppliersUseCase(get())
    }
    single {
        FirebaseUser()
    }
    single {
        UserRepository(get())
    }
    single {
        SuppliersUseCase(get())
    }
    viewModel {
        SupplierViewModel(get(), get(), get())
    }
    viewModel {
        CreateSupplierViewModel(get(), get())
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
        FirebaseItem()
    }
    single {
        ItemRepository(get())
    }
    single {
        ItemUseCase(get())
    }
    single {
        BillSupplierUseCases(get(), get())
    }
    viewModel {
        TheSupplierBillViewModel(get())
    }
    viewModel {
        TheSupplierViewModel(get(), get(), get(), get())
    }

}

val supplierAndTheSupplier = module {
    single { FirebaseSupplier() }
    single { SupplierRepository(get()) }
    single { SuppliersUseCase(get()) }
    viewModel { SupplierAndTheSupplierViewModel(get()) }
}
val supplierModule = listOf(supplier, createBillSupplier, supplierAndTheSupplier)