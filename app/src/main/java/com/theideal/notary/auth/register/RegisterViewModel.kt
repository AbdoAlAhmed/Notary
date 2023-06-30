package com.theideal.notary.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.User
import com.theideal.domain.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repoAuth: AuthenticationRepository
) : ViewModel() {

    private val _startMainActivity = MutableLiveData<Boolean>()
    val startMainActivity: LiveData<Boolean>
        get() = _startMainActivity

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    private val _navToUserInfo = MutableLiveData<Boolean>()
    val navToUserInfo: LiveData<Boolean>
        get() = _navToUserInfo


    fun doneStartMainActivity() {
        _startMainActivity.value = false
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            _progressBar.value = true
            repoAuth.createUserWithEmailAndPassword(user.email, user.getPassword()) {
                _snackBarMessage.value = it
                if (it == "Succeed") {
                    _navToUserInfo.value = true
                }
            }
            _progressBar.value = false
        }
    }

    fun doneNavToUserInfo() {
        _navToUserInfo.value = false
    }

}