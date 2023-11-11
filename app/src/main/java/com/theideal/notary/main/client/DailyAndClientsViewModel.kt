package com.theideal.notary.main.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.ClientsUseCases
import kotlinx.coroutines.launch

class DailyAndClientsViewModel( private val clientUseCases: ClientsUseCases): ViewModel() {

    private val _deleteDialogClient = MutableLiveData<Contact>()
    val deleteDialogClient: LiveData<Contact>
        get() = _deleteDialogClient

    private val _editDialogClient = MutableLiveData<Contact>()
    val editContact: LiveData<Contact>
        get() = _editDialogClient

    fun deleteDialogOpen(contact: Contact) {
        _deleteDialogClient.value = contact
    }

    fun deleteClient(contact: Contact) {
        viewModelScope.launch {
            clientUseCases.deleteClient(contact)
        }
    }

    fun deleteDialogClose() {
        _deleteDialogClient.value = Contact("")
    }

    fun editDialogOpen(contact: Contact) {
        _editDialogClient.value = contact
    }

    fun updateContactPhone(contact: Contact, keyValue: Map<String, Any>) {
        viewModelScope.launch {
            if (clientUseCases.clientPhoneExist(contact.phone)) {
                clientUseCases.updateClient(contact, keyValue)
            }
        }
    }

    fun updateContactName(contact: Contact, keyValue: Map<String, Any>) {
        viewModelScope.launch {
            clientUseCases.updateClient(contact, keyValue)
        }
    }

    fun editDialogClose() {
        _editDialogClient.value = Contact("")
    }
}