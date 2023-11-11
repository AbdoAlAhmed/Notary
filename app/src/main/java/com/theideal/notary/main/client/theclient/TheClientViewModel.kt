package com.theideal.notary.main.client.theclient

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.BillClientUseCases
import com.theideal.domain.usecases.ClientsUseCases
import com.theideal.notary.R
import kotlinx.coroutines.launch

class TheClientViewModel(
    private val clientsUseCase: ClientsUseCases,
    private val billClientUseCases: BillClientUseCases,
    private val app: Application

) : ViewModel() {

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _navToClientBill = MutableLiveData<Boolean>()
    val navToClientBill: LiveData<Boolean>
        get() = _navToClientBill

    private val _navToClientBillWithBillContact = MutableLiveData<BillContact>()
    val navToClientBillWithBillContact: LiveData<BillContact>
        get() = _navToClientBillWithBillContact

    private val _deleteBillConfirmDialog = MutableLiveData<BillContact>()
    val deleteBillConfirmDialog: LiveData<BillContact>
        get() = _deleteBillConfirmDialog

    private val _bills = MutableLiveData<List<BillContact>>()
    val bills: LiveData<List<BillContact>>
        get() = _bills


    private val _billContact = MutableLiveData<BillContact>()
    val billContact: LiveData<BillContact>
        get() = _billContact

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar


    private val _clientTotal = MutableLiveData<Double>()
    val clientTotal: LiveData<Double>
        get() = _clientTotal

    fun getContact(contactId: String) {
        viewModelScope.launch {
            _contact.value = clientsUseCase.getClient(contactId)
        }
    }

    fun setContact(contact: Contact) {
        _contact.value = contact
    }

    fun checkIfBillIsOpen(contactId: String) {
        viewModelScope.launch {
            if (!billClientUseCases.checkIfBillIsOpen(contactId)) {
                createBill(contactId)
            } else {
                snackBarMessage(app.getString(R.string.open_transactions_message))

            }
        }
    }


    fun editContactPhone(contact: Contact, keyValue: Map<String, Any>) {
        viewModelScope.launch {
            if (clientsUseCase.clientPhoneExist(contact.phone)) {
                clientsUseCase.updateClient(contact, keyValue)

            }
        }
    }

    fun editContactName(contact: Contact, keyValue: Map<String, Any>) {
        viewModelScope.launch {
            clientsUseCase.updateClient(contact, keyValue)
        }
    }


    fun createBill(contactId: String) {
        viewModelScope.launch {
            _billContact.value = billClientUseCases.createBill(contactId)
            _navToClientBill.value = true

        }
    }

    fun createBillWithLongClickIfThereIsBillOpened(contactId: String): Boolean {
        viewModelScope.launch {
            _billContact.value = billClientUseCases.createBill(contactId)
            _navToClientBill.value = true
        }
        return true
    }


    fun getBillsByContactId(contactId: String) {
        viewModelScope.launch {
            _bills.value = billClientUseCases.getBillsByContactId(contactId)
        }
    }

    fun navToClientBillComplete() {
        _navToClientBill.value = false
    }

    fun snackBarMessage(massage: String){
        _snackBar.value = massage
    }

    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun navToClientBillWithBillContact(billContact: BillContact) {
        _navToClientBillWithBillContact.value = billContact
    }

    fun navToClientBillWithBillContactComplete() {
        _navToClientBillWithBillContact.value = BillContact("")
    }

    fun deleteBillConfirmDialog(billContact: BillContact) {
        _deleteBillConfirmDialog.value = billContact
    }

    fun deleteBillConfirmDialogComplete() {
        _deleteBillConfirmDialog.value = BillContact("")
    }

    fun deleteBill(billContact: BillContact) {
        viewModelScope.launch {
            billClientUseCases.deleteBill(billContact.billId)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            clientsUseCase.deleteClient(contact)
        }
    }

    fun getTheClientTotal(contactId: String) {
        viewModelScope.launch {
            _clientTotal.value = billClientUseCases.clientTotal(contactId)
        }
    }

}