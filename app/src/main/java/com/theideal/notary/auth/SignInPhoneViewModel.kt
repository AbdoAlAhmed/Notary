package com.theideal.notary.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.domain.repository.AuthenticationRepository

class SignInPhoneViewModel(private val repo: AuthenticationRepository) :
    ViewModel() {

    private val _dialogPhoneNotEnabled = MutableLiveData<Boolean>()
    val dialogPhoneNotEnabled: LiveData<Boolean>
        get() = _dialogPhoneNotEnabled

    private val _navToSinInEmail = MutableLiveData<Boolean>()
    val navToSinInEmail: LiveData<Boolean>
        get() = _navToSinInEmail

    private val _navToCreateAccount = MutableLiveData<Boolean>()
    val navToCreateAccount: LiveData<Boolean>
        get() = _navToCreateAccount


    fun sigInPhone(phone: String){
        repo.sigInWithPhoneNumber(phone)
    }
    fun sigInPhoneNotEnabled() {
        _dialogPhoneNotEnabled.value = true
    }

    fun sigInPhoneNotEnabledComplete() {
        _dialogPhoneNotEnabled.value = false
    }

    fun navToSinInEmail() {
        _navToSinInEmail.value = true
    }

    fun navToSinInEmailComplete() {
        _navToSinInEmail.value = false
    }

    fun navToCreateAccount() {
        _navToCreateAccount.value = true
    }

    fun navToCreateAccountComplete() {
        _navToCreateAccount.value = false
    }
}