package com.theideal.notary.main.client.createclient

import android.app.Application
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

    private val _navToClientBillWithBillContact = MutableLiveData<BillContact>()
    val navToClientBillWithBillContact: LiveData<BillContact>
        get() = _navToClientBillWithBillContact

    private val _deleteBillConfirmDialog = MutableLiveData<BillContact>()
    val deleteBillConfirmDialog: LiveData<BillContact>
        get() = _deleteBillConfirmDialog

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

    private val _dialogEditTransferItem = MutableLiveData<Transfer>()
    val dialogEditTransferItem: LiveData<Transfer>
        get() = _dialogEditTransferItem

    private val _dialogDeleteTransferItem = MutableLiveData<Transfer>()
    val dialogDeleteTransferItem: LiveData<Transfer>
        get() = _dialogDeleteTransferItem

    fun getClient(contactId: String) {
        viewModelScope.launch {
            _contact.value = clientRepo.getClient(contactId)
        }
    }

    fun setClient(contact: Contact) {
        _contact.value = contact
    }

    fun checkIfBillIsOpen(contactId: String) {
        viewModelScope.launch {
            if (!createBillClientUseCases.checkIfBillIsOpen(contactId)) {
                createBill(contactId)
            } else {
                _snackBar.value = app.getString(R.string.open_transactions_message)

            }
        }
    }


    fun createBill(contactId: String) {
        viewModelScope.launch {
            _billContact.value =
                createBillClientUseCases.createBill(contactId)
            _navToClientBill.value = true

        }
    }

    fun createBillWithLongClickIfThereIsBillOpened(contact: Contact): Boolean {
        viewModelScope.launch {
            createBillClientUseCases.createBill(contact.contactId)
            _navToClientBill.value = true
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
                transfer.typeOfFinancialTransfer = "DEPOSIT"
                transferUseCase.addDeposit(transfer)
            }
        }
    }

    private fun checkTransfer(transfer: Transfer): Boolean {
        return transfer.typeOfFinancialTransfer == _typeOfFinancialTransfer.value
    }

    fun updateTransfer(transfer: Transfer) {
        viewModelScope.launch {
            if (checkTransfer(transfer)) {
                if (_typeOfFinancialTransfer.value == "WITHDRAW") {
                    transfer.typeOfFinancialTransfer = "WITHDRAW"
                    transferUseCase.updateWithdraw(transfer)
                } else {
                    transfer.typeOfFinancialTransfer = "DEPOSIT"
                    transferUseCase.updateDeposit(transfer)
                }
            } else {
                if (_typeOfFinancialTransfer.value == "WITHDRAW") {
                    transfer.typeOfFinancialTransfer = "WITHDRAW"
                    transferUseCase.deleteDeposit(transfer)
                    transferUseCase.updateWithdraw(transfer)
                } else {
                    transfer.typeOfFinancialTransfer = "DEPOSIT"
                    transferUseCase.deleteWithdraw(transfer)
                    transferUseCase.updateDeposit(transfer)
                }
            }
        }
    }

    fun deleteTransfer(transfer: Transfer) {
        viewModelScope.launch {
            if (_typeOfFinancialTransfer.value == "WITHDRAW") {
                transfer.typeOfFinancialTransfer = "WITHDRAW"
                transferUseCase.deleteWithdraw(transfer)
            } else {
                transfer.typeOfFinancialTransfer = "DEPOSIT"
                transferUseCase.deleteDeposit(transfer)
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

    fun editDialog(transfer: Transfer) {
        _dialogEditTransferItem.value = transfer
    }

    fun editDialogComplete() {
        _dialogEditTransferItem.value = Transfer("")
    }

    fun deleteDialog(transfer: Transfer) {
        _dialogDeleteTransferItem.value = transfer
    }

    fun deleteDialogComplete() {
        _dialogDeleteTransferItem.value = Transfer("")
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
            createBillClientUseCases.deleteBill(billContact.billId)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            clientRepo.deleteClient(contact)
        }
    }

}