package com.theideal.notary.di

import com.theideal.data.remote.FirebaseCompany
import com.theideal.domain.repository.CompanyRepository
import com.theideal.notary.main.company.CompanyViewModel
import com.theideal.notary.main.company.TransactionFeesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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