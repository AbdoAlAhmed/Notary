package com.theideal.notary.di

import com.theideal.domain.usecases.ClientsUseCases
import com.theideal.notary.main.more.MoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val more = module {
    single { ClientsUseCases(get(),get(),get()) }
    viewModel { MoreViewModel(get(),get()) }
}