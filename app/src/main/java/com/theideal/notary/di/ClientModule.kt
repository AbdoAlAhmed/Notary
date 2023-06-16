package com.theideal.notary.di

import com.theideal.data.remote.FirebaseBillClient
import com.theideal.data.remote.FirebaseBillSupplier
import com.theideal.data.remote.FirebaseClient
import com.theideal.data.remote.FirebaseContactDeposit
import com.theideal.data.remote.FirebaseContactWithdraw
import com.theideal.data.remote.FirebaseSupplier
import com.theideal.data.remote.FirebaseUser
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.repository.DepositRepository
import com.theideal.domain.repository.SupplierRepository
import com.theideal.domain.repository.UserRepository
import com.theideal.domain.repository.WithdrawRepository
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.domain.usecases.CreateBillClientUseCases
import com.theideal.domain.usecases.DepositUseCase
import com.theideal.domain.usecases.TransferUseCase
import com.theideal.domain.usecases.WithdrawUseCase
import com.theideal.notary.main.client.createclient.CreateClientViewModel
import com.theideal.notary.main.client.createclient.TheClientViewModel
import com.theideal.notary.main.client.createclient.bill.ClientBillViewModel
import com.theideal.notary.main.client.saletransactions.SaleTransactionsViewModel
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
        ClientRepository(get())
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
        CreateBillClientUseCases(get(), get(), get())
    }
    viewModel {
        TheClientViewModel(get(), get(), get(), get())
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
    viewModel {
        SaleTransactionsViewModel(get(), get(), get())
    }
    viewModel {
        CreateClientViewModel(get(), get(), get())
    }

}

val clientModule = listOf(transFerModule, ClientModule, BillClientModule, contactUseCases)
