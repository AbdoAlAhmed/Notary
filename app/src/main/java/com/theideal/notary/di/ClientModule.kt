package com.theideal.notary.di

import com.theideal.data.remote.FirebaseBillClient
import com.theideal.data.remote.FirebaseBillSupplier
import com.theideal.data.remote.FirebaseClient
import com.theideal.data.remote.FirebaseContactDeposit
import com.theideal.data.remote.FirebaseContactWithdraw
import com.theideal.data.remote.FirebaseItem
import com.theideal.data.remote.FirebaseSupplier
import com.theideal.data.remote.FirebaseUser
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.repository.DepositRepository
import com.theideal.domain.repository.ItemRepository
import com.theideal.domain.repository.SupplierRepository
import com.theideal.domain.repository.UserRepository
import com.theideal.domain.repository.WithdrawRepository
import com.theideal.domain.usecases.BillClientUseCases
import com.theideal.domain.usecases.ClientsUseCases
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.domain.usecases.DepositUseCase
import com.theideal.domain.usecases.ItemUseCase
import com.theideal.domain.usecases.TransferUseCase
import com.theideal.domain.usecases.WithdrawUseCase
import com.theideal.notary.main.client.createclient.CreateClientViewModel
import com.theideal.notary.main.client.daily.DailyViewModel
import com.theideal.notary.main.client.theclient.TheClientViewModel
import com.theideal.notary.main.client.theclient.bill.ClientBillViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transFerModule = module {
    single {
        FirebaseContactWithdraw()
    }
    single {
        WithdrawRepository(get())
    }
    single {
        WithdrawUseCase(get())
    }
    single {
        FirebaseContactDeposit()
    }
    single {
        DepositRepository(get())
    }
    single {
        DepositUseCase(get())
    }
    single {
        TransferUseCase(get(), get())
    }
}

val ClientModule = module {
    single {
        FirebaseClient()
    }
    single {
        FirebaseBillClient()
    }
    single {
        ClientRepository(get(), get())
    }
}

val BillClientModule = module {
    single {
        FirebaseBillSupplier()
    }
    single {
        BillSupplierRepository(get())
    }
    single {
        FirebaseBillClient()
    }
    single {
        BillClientRepository(get())
    }
    single {
        FirebaseSupplier()
    }
    single {
        SupplierRepository(get())
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
        BillClientUseCases(get(), get(), get(), get(), get())
    }
    viewModel {
        TheClientViewModel(get(), get(), get())
    }
    viewModel {
        ClientBillViewModel(get(), get(), get())
    }
}

val contactUseCases = module {
    single {
        FirebaseUser()
    }
    single {
        UserRepository(get())
    }
    single {
        ContactUseCases(get(), get())
    }
    single {
        ClientsUseCases(get(), get(), get())
    }
    viewModel {
        DailyViewModel(get(), get(), get())
    }
    viewModel {
        CreateClientViewModel(get(), get(), get())
    }
}

val clientModule = listOf(transFerModule, ClientModule, BillClientModule, contactUseCases)
