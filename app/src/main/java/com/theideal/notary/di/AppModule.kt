package com.theideal.notary.di

import com.theideal.data.remote.FirebaseAuthentication
import com.theideal.data.remote.FirebaseBillSupplier
import com.theideal.data.remote.FirebaseCompany
import com.theideal.data.remote.FirebaseSupplier
import com.theideal.data.remote.FirebaseUser
import com.theideal.domain.repository.AuthenticationRepository
import com.theideal.domain.repository.BillSupplierRepository
import com.theideal.domain.repository.CompanyRepository
import com.theideal.domain.repository.SupplierRepository
import com.theideal.domain.repository.UserRepository
import com.theideal.domain.usecases.CreateBillSupplierUseCases
import com.theideal.domain.usecases.SupplierUseCase
import com.theideal.notary.auth.AuthenticationViewModel
import com.theideal.notary.auth.SignInEmailViewModel
import com.theideal.notary.auth.SignInInformationViewModel
import com.theideal.notary.auth.SignInPhoneViewModel
import com.theideal.notary.main.client.createclient.bill.ClientBillViewModel
import com.theideal.notary.main.company.CompanyViewModel
import com.theideal.notary.main.company.TransactionFeesViewModel
import com.theideal.notary.main.supplier.SupplierViewModel
import com.theideal.notary.main.supplier.theSupplier.CreateSupplierViewModel
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

    viewModel {
        SupplierViewModel(get(), get())
    }

}

val createSupplier = module {
    viewModel {
        CreateSupplierViewModel(get())
    }
}
val theSupplier = module {
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
        TheSupplierViewModel(get(), get())
    }
}

val theSupplierBill = module {
    viewModel {
        TheSupplierBillViewModel(get())
    }
}