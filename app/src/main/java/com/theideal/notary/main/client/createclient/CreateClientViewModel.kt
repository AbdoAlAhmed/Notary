package com.theideal.notary.main.client.createclient

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.notary.R
import kotlinx.coroutines.launch

class CreateClientViewModel(
    private val clientRepo: ClientRepository,
    private val contactUseCases: ContactUseCases,
    private val app: Application,
) : ViewModel() {

    private val _clientCreatedNavToTheClient = MutableLiveData<Boolean>()
    val clientCreatedNavToTheClient: LiveData<Boolean>
        get() = _clientCreatedNavToTheClient

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    private suspend fun checkTheNumberToCreateContact(phone: Contact) =
        contactUseCases.checkTheNumberToCreateContact(phone.phone)

    fun createClient(contact: Contact) {
        viewModelScope.launch {
            if (checkTheNumberToCreateContact(contact)) {
                clientRepo.createClient(contact)
                _clientCreatedNavToTheClient.postValue(true)
            } else {
                _snackBarMessage.postValue(app.getString(R.string.phone_already_exists))
            }
        }
    }

    fun doneNavigatingToTheClient() {
        _clientCreatedNavToTheClient.value = false
    }

    fun doneShowingSnackBar() {
        _snackBarMessage.value = ""
    }

}