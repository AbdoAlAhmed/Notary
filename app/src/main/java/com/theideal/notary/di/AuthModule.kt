package com.theideal.notary.di

import com.theideal.data.remote.FirebaseAuthentication
import com.theideal.data.remote.FirebaseUser
import com.theideal.domain.repository.AuthenticationRepository
import com.theideal.domain.repository.UserRepository
import com.theideal.notary.auth.AuthenticationViewModel
import com.theideal.notary.auth.SignInEmailViewModel
import com.theideal.notary.auth.SignInPhoneViewModel
import com.theideal.notary.auth.register.RegisterViewModel
import com.theideal.notary.auth.register.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val auth = module {
    single {
        FirebaseAuthentication()
    }
    single {
        AuthenticationRepository(get())
    }
    viewModel {
        AuthenticationViewModel(get(),get())
    }
    viewModel {
        SignInPhoneViewModel(get())
    }
    viewModel {
        SignInEmailViewModel(get())
    }

}

val user = module {
    single {
        FirebaseUser()
    }
    single {
        UserRepository(get())
    }

}
val register = module {

    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        UserInfoViewModel(get())
    }
}
val authModule = listOf(auth, register, user)




