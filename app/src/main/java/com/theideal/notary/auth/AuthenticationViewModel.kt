package com.theideal.notary.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.domain.repository.AuthenticationRepository
import com.theideal.notary.R
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val repo: AuthenticationRepository,
    private val app: Application
) : ViewModel() {

    private var _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIN: LiveData<Boolean>
        get() = _isUserLoggedIn

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val navToSignIn = MutableLiveData<Boolean>()
    val navToSignInFragment: LiveData<Boolean>
        get() = navToSignIn

    fun isUserLoggedIn() {
        _isUserLoggedIn.value = repo.isUserLoggedIn()
    }

    fun sigInWithPhoneNumber(phoneNumber: String) {
        repo.sigInWithPhoneNumber(phoneNumber)
    }

    fun forgetPassword(email: String) {
        viewModelScope.launch {
            repo.forgetPassword(email)
            snackBarMessage(app.getString(R.string.check_your_email))
            navToSignInFragment()
        }
    }

    fun navToSignInFragment() {
        navToSignIn.value = true
    }

    fun doneNavToSignInFragment(){
        navToSignIn.value = false
    }
    fun snackBarMessage(message: String) {
        _snackBarMessage.value = message
    }

    fun snackBarMessageDisplayed() {
        _snackBarMessage.value = ""
    }
}