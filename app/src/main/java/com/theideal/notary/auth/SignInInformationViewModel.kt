package com.theideal.notary.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.User
import com.theideal.domain.repository.AuthenticationRepository
import com.theideal.domain.repository.UserRepository
import kotlinx.coroutines.launch

class SignInInformationViewModel(
    private val repoAuth: AuthenticationRepository,
    private val repoUser: UserRepository,
) :
    AuthenticationViewModel(repoAuth) {

    private val _startMainActivity = MutableLiveData<Boolean>()
    val startMainActivity: LiveData<Boolean>
        get() = _startMainActivity


    fun startMainActivity() {
        _startMainActivity.value = true
    }

    fun doneStartMainActivity() {
        _startMainActivity.value = false
    }

    fun uploadUserInfo(user: User) {
        _progressBar.value = true
        viewModelScope.launch {
            repoUser.uploadUserInfo(user) {
                _snackBarMessage.value = it
                if (it == "success") {
                    signInEmail(user)
                }
            }
        }
    }

    private fun signInEmail(user: User) {
        repoAuth.signInWithEmailAndPassword(user.email, user.getPassword()) {
            _snackBarMessage.value = it
            if (it == "success") {
                _isUserLoggedIn.value = true
                _progressBar.value = false
                startMainActivity()
            } else {
                _progressBar.value = false
                _snackBarMessage.value = it
            }
        }
    }

}