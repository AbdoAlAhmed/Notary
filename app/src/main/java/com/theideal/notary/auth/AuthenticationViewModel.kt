package com.theideal.notary.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.domain.repository.AuthenticationRepository

 class AuthenticationViewModel(private val repo: AuthenticationRepository) : ViewModel() {

    private var _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIN: LiveData<Boolean>
        get() = _isUserLoggedIn

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar



    fun isUserLoggedIn() {
        _isUserLoggedIn.value = repo.isUserLoggedIn()
    }

    fun sigInWithPhoneNumber(phoneNumber: String) {
        repo.sigInWithPhoneNumber(phoneNumber)
    }
}