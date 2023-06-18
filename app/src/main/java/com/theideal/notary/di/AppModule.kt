package com.theideal.notary.di

import com.theideal.data.remote.FirebaseAuthentication
import com.theideal.data.remote.FirebaseBillSupplier
import com.theideal.data.remote.FirebaseCompany
import com.theideal.data.remote.FirebaseUser
import com.theideal.domain.repository.AuthenticationRepository
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.CompanyRepository
import com.theideal.domain.repository.UserRepository
import com.theideal.domain.usecases.CreateBillSupplierUseCases
import com.theideal.notary.auth.AuthenticationViewModel
import com.theideal.notary.auth.SignInEmailViewModel
import com.theideal.notary.auth.SignInInformationViewModel
import com.theideal.notary.auth.SignInPhoneViewModel
import com.theideal.notary.main.company.CompanyViewModel
import com.theideal.notary.main.company.TransactionFeesViewModel
import com.theideal.notary.main.supplier.theSupplier.TheSupplierBillViewModel
import com.theideal.notary.main.supplier.theSupplier.TheSupplierViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val authModule = module {
    single {
        FirebaseAuthentication()
    }
    single {
        AuthenticationRepository(get())
    }
    single {
        FirebaseUser()
    }
    single {
        UserRepository(get())
    }
    viewModel {
        AuthenticationViewModel(get())
    }
    viewModel {
        SignInPhoneViewModel(get())
    }
    viewModel {
        SignInEmailViewModel(get())
    }
    viewModel {
        SignInInformationViewModel(get(), get())
    }
}


val companyModule = module {
    single {
        FirebaseCompany()
    }
    single {
        CompanyRepository(get())
    }

    viewModel {
        CompanyViewModel(get(), get())
    }
    viewModel {
        TransactionFeesViewModel(get(), get())
    }
}


