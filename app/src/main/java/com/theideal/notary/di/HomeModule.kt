package com.theideal.notary.di

import com.theideal.data.remote.FirebaseBillClient
import com.theideal.data.remote.FirebaseClient
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.usecases.ClientsUseCases
import com.theideal.notary.main.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeModule = module {

    viewModel { HomeViewModel(get()) }
}