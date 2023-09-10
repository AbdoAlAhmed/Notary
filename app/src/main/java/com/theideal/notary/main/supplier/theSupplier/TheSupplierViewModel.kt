package com.theideal.notary.main.supplier.theSupplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Transfer
import com.theideal.domain.usecases.BillSupplierUseCases
import com.theideal.domain.usecases.SupplierUseCase
import com.theideal.domain.usecases.TransferUseCase
import kotlinx.coroutines.launch

class TheSupplierViewModel(
    private val supplierUseCase: SupplierUseCase,
    private val createBillUseCase: BillSupplierUseCases,
    private val transferUseCase: TransferUseCase

) : ViewModel() {


    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _billContact = MutableLiveData<BillContact>()
    val billContact: LiveData<BillContact>
        get() = _billContact

    private val _navToSupplierBill = MutableLiveData<Boolean>()
    val navToSupplierBill: LiveData<Boolean>
        get() = _navToSupplierBill

    private val _billList = MutableLiveData<List<BillContact>>()
    val billList: LiveData<List<BillContact>>
        get() = _billList

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    private val _transfers = MutableLiveData<List<Transfer>>()
    val transfers: LiveData<List<Transfer>>
        get() = _transfers

    private val _typeOfFinancialTransfer = MutableLiveData<String>()
    val typeOfFinancialTransfer: LiveData<String>
        get() = _typeOfFinancialTransfer

    private val _dialogDeleteTransferItem = MutableLiveData<Transfer>()
    val dialogDeleteTransferItem: LiveData<Transfer>
        get() = _dialogDeleteTransferItem

    private val _dialogEditTransferItem = MutableLiveData<Transfer>()
    val dialogEditTransferItem: LiveData<Transfer>
        get() = _dialogEditTransferItem

    private val _startTransferDialog = MutableLiveData<Boolean>()
    val startTransferDialog: LiveData<Boolean>
        get() = _startTransferDialog

    fun getBillBySupplierId(contactId: String) {
        viewModelScope.launch {
            _billList.value = createBillUseCase.getBills(contactId)
        }
    }

    fun getSupplier(contactId: String) {
        viewModelScope.launch {
            try {
                _contact.value = supplierUseCase.getSupplierWithId(contactId).firstOrNull()
            } catch (e: Exception) {
                _contact.value = Contact()
            }
        }
    }

    fun returnContact(): Contact {
        return _contact.value!!
    }

    fun setSupplier(contact: Contact) {
        _contact.value = contact
    }

    // TODO: 2021-08-17  make it like the client
    fun createBillSupplier(contact: Contact) {
        viewModelScope.launch {
            createBillUseCase.createBill(BillContact(), contact)
            _navToSupplierBill.value = true
        }
    }

    fun navToSupplierBillComplete() {
        _navToSupplierBill.value = false
    }


    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun getTransfersWithContactId(contactId: String) {
        viewModelScope.launch {
            _transfers.value = transferUseCase.getListOfTransfer(contactId)
        }
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
            if (transfer.typeOfFinancialTransfer == "WITHDRAW") {
                transferUseCase.deleteWithdraw(transfer)
            } else {
                transferUseCase.deleteDeposit(transfer)
            }
        }
    }

    fun setTypeOfFinancialTransfer(typeOfFinancialTransfer: String) {
        _typeOfFinancialTransfer.value = typeOfFinancialTransfer
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

    fun startTransferDialog() {
        _startTransferDialog.value = true
    }

    fun startTransferDialogComplete() {
        _startTransferDialog.value = false
    }


}
