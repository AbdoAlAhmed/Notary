package com.theideal.notary.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.domain.repository.AuthenticationRepository
import kotlinx.coroutines.launch

open class AuthenticationViewModel(private val repo: AuthenticationRepository) : ViewModel() {

    var _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIN: LiveData<Boolean>
        get() = _isUserLoggedIn

    val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    init {
        Log.i("AuthenticationViewModel", "_isSignIn: ${_isUserLoggedIn.value}")
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String) {
        repo.signInWithEmailAndPassword(email, password) {

        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            repo.createUserWithEmailAndPassword(email, password) {

            }
        }
    }

    fun isUserLoggedIn() {
        _isUserLoggedIn.value = repo.isUserLoggedIn()
    }

    fun sigInWithPhoneNumber(phoneNumber: String) {
        repo.sigInWithPhoneNumber(phoneNumber)
    }
}