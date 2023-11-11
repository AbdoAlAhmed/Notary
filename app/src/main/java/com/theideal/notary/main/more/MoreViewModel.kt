package com.theideal.notary.main.more

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.MoreItem
import com.theideal.data.model.User
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.notary.R
import kotlinx.coroutines.launch

class MoreViewModel(private val contactUseCase: ContactUseCases, private val app: Application) :
    ViewModel() {

    private val listOfMoreItems = listOf(
        MoreItem(app.getString(R.string.profile), app.getString(R.string.under_development), 1),
        MoreItem(app.getString(R.string.setting), app.getString(R.string.under_development), 2),
        MoreItem(
            app.getString(R.string.notary_wallet),
            app.getString(R.string.under_development),
            3
        ),
        MoreItem(app.getString(R.string.help), app.getString(R.string.under_development), 4),
        MoreItem(app.getString(R.string.about), app.getString(R.string.under_development), 5),
        MoreItem(app.getString(R.string.logout), 6)
    )

    private val _moreItems = listOfMoreItems
    val moreItems: List<MoreItem>
        get() = _moreItems

    private val _user = MutableLiveData<User>()
    val user: MutableLiveData<User>
        get() = _user

    private val _logout = MutableLiveData<Boolean>()
    val logout: MutableLiveData<Boolean>
        get() = _logout

    fun getContact() {
        viewModelScope.launch {
            _user.value = contactUseCase.getUserInfo()
        }
    }

    fun logout() {
        contactUseCase.logout()
        _logout.value = true

    }

    fun clickItem(id: Int) {
        when (id) {
            1 -> {
            }

            2 -> {
            }

            3 -> {
            }

            4 -> {
            }

            5 -> {
            }

            6 -> {
                logout()
            }
        }
    }

}