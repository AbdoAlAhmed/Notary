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
}