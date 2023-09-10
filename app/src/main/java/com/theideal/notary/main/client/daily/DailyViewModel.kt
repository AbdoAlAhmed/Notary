package com.theideal.notary.main.client.daily

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.usecases.ClientsUseCases
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.notary.R
import kotlinx.coroutines.launch

class DailyViewModel(
    private val useCase: ContactUseCases,
    private val app: Application,
    private val clientRepo: ClientRepository,
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

    private val _clients = MutableLiveData<List<Contact>?>()
    val clients: LiveData<List<Contact>?>
        get() = _clients




    init {

    }

    fun checkUserInfo() {
        viewModelScope.launch {
            try {
                val result = useCase.checkUserInfoToCreateContact()
                when (result) {
                    "NoCompany" -> {
                        _startCompanyActivity.value = true
                        _snackBar.value = app.getString(R.string.create_company_error)
                        // _startCompanyActivity.value = false it's not working

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


    fun getAllClients() {
        viewModelScope.launch {
            try {
                _clients.value = clientRepo.getClientByUserId()

            } catch (e: Exception) {
                _snackBar.value = app.getString(R.string.error_getting_the_data)
            }
        }
    }

    fun createClientStarting() {
        _createClient.value = false
    }

    fun snackBarShown() {
        _snackBar.value = ""
    }

    fun startCompanyActivityComplete() {
        _startCompanyActivity.value = false
    }

    private fun startCompanyActivity() {
        _startCompanyActivity.value = true
    }

}