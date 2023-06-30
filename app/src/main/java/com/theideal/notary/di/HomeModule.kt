package com.theideal.notary.di

import com.theideal.notary.main.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeModule = module {
    viewModel { HomeViewModel(get()) }
}