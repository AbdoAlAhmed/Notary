package com.theideal.notary.main.client.createclient

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Transfer
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.usecases.CreateBillClientUseCases
import com.theideal.domain.usecases.TransferUseCase
import com.theideal.notary.R
import kotlinx.coroutines.launch

class TheClientViewModel(
    private val clientRepo: ClientRepository,
    private val createBillClientUseCases: CreateBillClientUseCases,
    private val transferUseCase: TransferUseCase,
    private val app: Application

) : ViewModel() {

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _navToClientBill = MutableLiveData<Boolean>()
    val navToClientBill: LiveData<Boolean>
        get() = _navToClientBill


    private val _bills = MutableLiveData<List<BillContact>>()
    val bills: LiveData<List<BillContact>>
        get() = _bills


    private val _transfers = MutableLiveData<List<Transfer>>()
    val transfers: LiveData<List<Transfer>>
        get() = _transfers

    private val _billContact = MutableLiveData<BillContact>()
    val billContact: LiveData<BillContact>
        get() = _billContact

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    private val _startTransferDialog = MutableLiveData<Boolean>()
    val startTransferDialog: LiveData<Boolean>
        get() = _startTransferDialog

    private val _typeOfFinancialTransfer = MutableLiveData<String>()

    fun getClient(contactId: String) {
        viewModelScope.launch {
            _contact.value = clientRepo.getClient(contactId)
        }
    }

    fun setClient(contact: Contact) {
        _contact.value = contact
    }


    fun createBill(contact: Contact) {
        viewModelScope.launch {
            val status = createBillClientUseCases.checkIfBillIsOpen(contact.contactId).status
            Log.i("status", status)
            _billContact.value =
                if (status != "open") {
                    _navToClientBill.value = true
                    createBillClientUseCases.createBill(contact.contactId)

                } else {
                    _snackBar.value = app.getString(R.string.open_transactions_message)
                    BillContact()
                }

        }
    }

    fun createBillWithLongClickIfThereIsBillOpened(contact: Contact): Boolean {
        viewModelScope.launch {
            createBillClientUseCases.createBill(contact.contactId)
            _navToClientBill.postValue(true)
        }
        return true
    }


    fun getBillsByContactId(contactId: String) {
        viewModelScope.launch {
            _bills.postValue(createBillClientUseCases.getBillsByContactId(contactId))
        }
    }

    fun navToClientBillComplete() {
        _navToClientBill.value = false
    }


    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun startTransferDialog() {
        _startTransferDialog.value = true
    }

    fun startTransferDialogComplete() {
        _startTransferDialog.value = false
    }

    fun addTransfer(transfer: Transfer) {
        viewModelScope.launch {
            if (_typeOfFinancialTransfer.value == "WITHDRAW") {
                transfer.typeOfFinancialTransfer = "WITHDRAW"
                transferUseCase.addWithdraw(transfer)
            } else {
                transferUseCase.addDeposit(transfer)
            }
        }
    }

    fun setTypeOfFinancialTransfer(typeOfFinancialTransfer: String) {
        _typeOfFinancialTransfer.value = typeOfFinancialTransfer
    }

    fun getTransfersWithContactId(contactId: String) {
        viewModelScope.launch {
            _transfers.value = transferUseCase.getListOfTransfer(contactId)
        }
    }
}