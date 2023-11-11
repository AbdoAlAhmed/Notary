package com.theideal.notary.main.client.daily

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.ClientsUseCases
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.notary.R
import kotlinx.coroutines.launch

class DailyViewModel(
    private val useCase: ContactUseCases,
    private val app: Application,
    private val clientUseCases: ClientsUseCases
) : ViewModel() {

    private val _createClient = MutableLiveData<Boolean>()
    val createClient: LiveData<Boolean>
        get() = _createClient

    private val _startCompanyActivity = MutableLiveData<Boolean>()
    val startCompanyActivity: LiveData<Boolean>
        get() = _startCompanyActivity
    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    private val _clientsToday = MutableLiveData<List<Pair<Contact,BillContact>>?>()
    val clientsToday: LiveData<List<Pair<Contact,BillContact>>?>
        get() = _clientsToday

    private val _allClient = MutableLiveData<List<Contact>?>()
    val allClient: LiveData<List<Contact>?>
        get() = _allClient

    private val _moveToTheClientFromSearch = MutableLiveData<Contact>()
    val moveToTheClientFromSearch: LiveData<Contact>
        get() = _moveToTheClientFromSearch

    private val _totalPaidMoney = MutableLiveData<Double>()
    val totalPaidMoney: LiveData<Double>
        get() = _totalPaidMoney




    init {
        getTotalMoney()
    }

    fun checkUserInfo() {
        viewModelScope.launch {
            try {
                val result = useCase.checkUserInfoToCreateContact()
                when (result) {
                    "NoCompany" -> {
                        _startCompanyActivity.value = true
                        _snackBar.value = app.getString(R.string.create_company_error)
                    }

                    "NoUser" -> {
                        _snackBar.value = app.getString(R.string.no_user)
                    }

                    "Success" -> {
                        _createClient.value = true
                    }

                    else -> {
                        // Handle other error cases
                    }
                }
            } catch (e: Exception) {
                _snackBar.value = app.getString(R.string.error_getting_the_data)
            }
        }
    }

    fun getTotalMoney() {
        viewModelScope.launch {
            _totalPaidMoney.value = clientUseCases.getAllPayBookToday()
        }
    }

    fun getAllClientsToday() {
        viewModelScope.launch {
            try {
                Log.d("getAllClientsToday", "getAllClientsToday: ${clientUseCases.getClientsToday()}")
                _clientsToday.value = clientUseCases.getClientsToday()
                _allClient.value = clientUseCases.getClientsByUserId()

            } catch (e: Exception) {
                _snackBar.value = e.message


            }
        }

    }


    fun moveToTheClientFromSearchDaily(contact: Contact) {
        _moveToTheClientFromSearch.value = contact
    }

    fun moveToTheClientFromSearchDailyCompleted() {
        _moveToTheClientFromSearch.value!!.contactId = ""
    }

    fun createClientStarting() {
        _createClient.value = false
    }

    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun snackBar(message: String) {
        _snackBar.value = message
    }


}