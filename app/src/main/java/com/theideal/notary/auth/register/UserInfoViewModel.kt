package com.theideal.notary.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.User
import com.theideal.domain.repository.UserRepository
import kotlinx.coroutines.launch

class UserInfoViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    private val _startMainActivity = MutableLiveData<Boolean>()
    val startMainActivity: LiveData<Boolean>
        get() = _startMainActivity


    fun uploadUserInfo(user: User) {
        _progressBar.value = true
        viewModelScope.launch {
            userRepository.uploadUserInfo(user) {
                _snackBar.value = it
            }
            _startMainActivity.value = true
            _progressBar.value = false
        }
    }

    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun startMainActivityComplete() {
        _startMainActivity.value = false
    }
}