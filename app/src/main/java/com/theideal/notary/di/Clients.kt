package com.theideal.notary.di

import com.theideal.data.remote.FirebaseBillClient
import com.theideal.data.remote.FirebaseClient
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.usecases.ClientsUseCases
import com.theideal.notary.main.client.DailyAndClientsViewModel
import com.theideal.notary.main.client.allclient.ClientsFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val clients = module {
    single { FirebaseBillClient() }
    single { FirebaseClient() }
    single { BillClientRepository(get()) }
    single { ClientRepository(get(), get()) }
    single { ClientsUseCases(get(), get(),get()) }
    viewModel { ClientsFragmentViewModel(get()) }
    viewModel { DailyAndClientsViewModel(get()) }
}