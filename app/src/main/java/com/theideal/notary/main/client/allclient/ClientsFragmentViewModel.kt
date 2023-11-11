package com.theideal.notary.main.client.allclient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.ClientsUseCases
import kotlinx.coroutines.launch

class ClientsFragmentViewModel(private val clientsUseCases: ClientsUseCases) : ViewModel() {


    private val _clients = MutableLiveData<List<Contact>>()
    val clients: LiveData<List<Contact>>
        get() = _clients

    private val _moveToTheClientFromSearch = MutableLiveData<Contact>()
    val moveToTheClientFromSearch: LiveData<Contact>
        get() = _moveToTheClientFromSearch

    private val _totalDept = MutableLiveData<Double>()
    val totalDept: LiveData<Double>
        get() = _totalDept

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    fun getAllClients() {
        viewModelScope.launch {
            _clients.value = clientsUseCases.getClientsByUserId()
        }
    }

    fun getTotalDepts() {
        viewModelScope.launch {
            _totalDept.value = clientsUseCases.getDeptMoney()
        }
    }

    fun moveToTheClientFromSearch(contact: Contact) {
        _moveToTheClientFromSearch.value = contact
        Log.d("moveToTheClientFromSearch", "moveToTheClientFromSearch: ${contact.name}")
    }

    fun doneMoveToTheClientFromSearch() {
        _moveToTheClientFromSearch.value!!.contactId = ""
    }

    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun snackBar(message: String) {
        _snackBar.value = message
    }
}