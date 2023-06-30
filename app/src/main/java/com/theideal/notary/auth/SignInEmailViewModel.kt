package com.theideal.notary.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.User
import com.theideal.domain.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class SignInEmailViewModel(private val repo: AuthenticationRepository) : ViewModel() {

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _startMainActivity = MutableLiveData<Boolean>()
    val startMainActivity: LiveData<Boolean>
        get() = _startMainActivity

    init {
        _progressBar.value = false
    }

    fun signInWithEmail(user: User) {
        _progressBar.value = true
        viewModelScope.launch {
            repo.signInWithEmailAndPassword(user.email, user.getPassword()) {
                _snackBarMessage.value = it
                if (it == "Succeed") {
                    _startMainActivity.value = true
                }
            }
            _progressBar.value = false
        }
    }


    fun snackBarComplete() {
        _snackBarMessage.value = ""
    }

    fun startMainActivityComplete() {
        _startMainActivity.value = false
    }
}