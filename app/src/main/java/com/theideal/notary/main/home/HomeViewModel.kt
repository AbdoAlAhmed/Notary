package com.theideal.notary.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.data.model.User
import com.theideal.domain.repository.AuthenticationRepository

class HomeViewModel(private val authRepo: AuthenticationRepository) : ViewModel() {

    private val _navToCompleteUserInfo = MutableLiveData<Boolean>()
    val navToCompleteUserInfo: LiveData<Boolean>
        get() = _navToCompleteUserInfo

    private val _signOut = MutableLiveData<Boolean>()
    val signOut: LiveData<Boolean>
        get() = _signOut


    fun checkUserInfoComplete(user: User) {
        if (user.name == "" || user.phone == "") {
            _navToCompleteUserInfo.value = true
        }
    }

    fun navToCompleteUserInfoComplete() {
        _navToCompleteUserInfo.value = false
    }

    fun signOut() {
        authRepo.signOut()
        _signOut.value = true
    }
}