package com.theideal.notary.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.theideal.data.model.User
import com.theideal.domain.repository.AuthenticationRepository

class SignInEmailViewModel(private val repo: AuthenticationRepository) :
    AuthenticationViewModel(repo) {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _navToCreateAccountPage2 = MutableLiveData<Boolean>()
    val navToCreateAccountPage2: LiveData<Boolean>
        get() = _navToCreateAccountPage2

    init {

        _navToCreateAccountPage2.postValue(false)
        _progressBar.postValue(false)
    }

    private fun signInEmail(user: User) {
        _progressBar.value = true
        repo.signInWithEmailAndPassword(user.email, user.getPassword()) {
            when (it) {
                "success" -> {
                    _isUserLoggedIn.value = true
                    _snackBarMessage.value = it
                    _progressBar.value = false
                }

                "EmailNotFound" -> {
                    createUser(user)
                    _progressBar.value = false
                }

                else -> {
                    _snackBarMessage.value = it
                    _progressBar.value = false
                }
            }
        }

    }

    fun createUser(user: User) {
        _progressBar.value = true
        repo.createUserWithEmailAndPassword(user.email, user.getPassword()) {
            // created true direct to get more info
            when (it) {
                "success" -> {
                    _navToCreateAccountPage2.value = true
                    _snackBarMessage.value = it
                    _progressBar.value = false
                }

                "EmailUsed" -> {
                    _progressBar.value = false
                    _snackBarMessage.value = it
                    signInEmail(user)
                }

                else -> {
                    _progressBar.value = false
                    _snackBarMessage.value = it
                }
            }
        }


    }

    fun navToCreateAccountPage2Done() {
        _navToCreateAccountPage2.value = false
    }
}