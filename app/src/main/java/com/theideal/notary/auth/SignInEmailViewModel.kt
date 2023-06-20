package com.theideal.notary.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.User
import com.theideal.domain.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class SignInEmailViewModel(private val repo: AuthenticationRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _navToCreateAccountPage2 = MutableLiveData<Boolean>()
    val navToCreateAccountPage2: LiveData<Boolean>
        get() = _navToCreateAccountPage2

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    init {

        _navToCreateAccountPage2.postValue(false)
        _progressBar.value = false
    }

    fun signInWithEmail(user: User) {
        _progressBar.value = true
        viewModelScope.launch {
            repo.signInWithEmailAndPassword(user.email, user.getPassword()) {
                _snackBarMessage.value = it
                _progressBar.value = false
            }
        }
    }


    fun navToCreateAccount() {
        _navToCreateAccountPage2.value = true
    }

    fun navToCreateAccountPage2Done() {
        _navToCreateAccountPage2.value = false
    }

    fun snackBarComplete() {
        _snackBarMessage.value = ""
    }
}