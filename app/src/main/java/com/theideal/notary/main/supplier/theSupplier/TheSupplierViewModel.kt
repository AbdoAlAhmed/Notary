package com.theideal.notary.main.supplier.theSupplier

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Transfer
import com.theideal.domain.usecases.BillSupplierUseCases
import com.theideal.domain.usecases.SuppliersUseCase
import com.theideal.domain.usecases.TransferUseCase
import com.theideal.notary.R
import kotlinx.coroutines.launch

class TheSupplierViewModel(
    private val suppliersUseCase: SuppliersUseCase,
    private val billSupplierUseCase: BillSupplierUseCases,
    private val transferUseCase: TransferUseCase,
    private val app: Application

) : ViewModel() {


    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _billContact = MutableLiveData<BillContact>()
    val billContact: LiveData<BillContact>
        get() = _billContact


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

    private val _navToTheBillSupplier = MutableLiveData<BillContact>()
    val navToTheBillSupplier: LiveData<BillContact>
        get() = _navToTheBillSupplier

    private val _dialogDeleteBill = MutableLiveData<BillContact>()
    val dialogDeleteBill: LiveData<BillContact>
        get() = _dialogDeleteBill

    private val _totalMoney = MutableLiveData<Double>()
    val totalMoney: LiveData<Double>
        get() = _totalMoney



    fun getTotalMoney(contactId: String)  {

        viewModelScope.launch {
            val transferTotal = transferUseCase.calculateTransfer(contactId)
            val itemTotal = billSupplierUseCase.getTotalMoney(contactId)
           _totalMoney.value = transferTotal + itemTotal
        }

    }

    fun getBillBySupplierId(contactId: String) {
        viewModelScope.launch {
            _billList.value = billSupplierUseCase.getBills(contactId)
        }
    }

    fun getSupplier(contactId: String) {
        viewModelScope.launch {
            _contact.value = suppliersUseCase.getSupplierWithId(contactId)!!.first()
        }
    }


    fun setSupplier(contact: Contact) {
        _contact.value = contact
    }


    fun checkBillOpen(contactId: String) {
        viewModelScope.launch {
            if (!checkIsOpen(contactId)) {
                createBill(contactId)
            } else {
                _snackBar.value = app.getString(R.string.open_transactions_message)
            }
        }
    }

    private fun createBill(contactId: String) {
        viewModelScope.launch {
            _billContact.value = billSupplierUseCase.createBill(contactId)
            navToTheSupplierBill(_billContact.value!!)
        }
    }

    private suspend fun checkIsOpen(contactId: String): Boolean {
        val list = billSupplierUseCase.checkIfBillIsOpen(contactId)
        var isOpen = false
        for (bill in list) {
            if (bill.status == app.getString(R.string.open)) {
                isOpen = true
                break
            }
        }
        return isOpen
    }

    fun createBillWithLongClickIfThereIsBillOpened(contactId: String): Boolean {
        createBill(contactId)
        return true
    }


    private fun navToTheSupplierBill(billContact: BillContact) {
        _navToTheBillSupplier.value = billContact
    }

    fun navToTheSupplierBillComplete() {
        _navToTheBillSupplier.value = BillContact("")
    }

    fun snackBarMessage(message: String) {
        _snackBar.value = message
    }

    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun dialogDeleteBill(billContact: BillContact) {
        _dialogDeleteBill.value = billContact
    }

    fun dialogDeleteBillComplete() {
        _dialogDeleteBill.value = BillContact("")
    }

    fun getTransfersWithContactId(contactId: String) {
        viewModelScope.launch {
            _transfers.value = transferUseCase.getListOfTransfer(contactId)
        }
    }

    fun addTransfer(transfer: Transfer) {
        viewModelScope.launch {
            if (_typeOfFinancialTransfer.value == app.getString(R.string.withdraw)) {
                transfer.typeOfFinancialTransfer = app.getString(R.string.withdraw)
                transferUseCase.addWithdraw(transfer)
            } else {
                transfer.typeOfFinancialTransfer = app.getString(R.string.deposit)
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
                if (_typeOfFinancialTransfer.value == app.getString(R.string.withdraw)) {
                    transfer.typeOfFinancialTransfer = app.getString(R.string.withdraw)
                    transferUseCase.updateWithdraw(transfer)
                } else {
                    transfer.typeOfFinancialTransfer = app.getString(R.string.deposit)
                    transferUseCase.updateDeposit(transfer)
                }
            } else {
                if (_typeOfFinancialTransfer.value == app.getString(R.string.withdraw)) {
                    transfer.typeOfFinancialTransfer = app.getString(R.string.withdraw)
                    transferUseCase.deleteDeposit(transfer)
                    transferUseCase.updateWithdraw(transfer)
                } else {
                    transfer.typeOfFinancialTransfer = app.getString(R.string.deposit)
                    transferUseCase.deleteWithdraw(transfer)
                    transferUseCase.updateDeposit(transfer)
                }
            }
        }
    }

    fun deleteTransfer(transfer: Transfer) {
        viewModelScope.launch {
            if (transfer.typeOfFinancialTransfer == app.getString(R.string.withdraw)) {
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

    fun deleteDialogTransfer(transfer: Transfer) {
        _dialogDeleteTransferItem.value = transfer
    }

    fun deleteDialogTransferComplete() {
        _dialogDeleteTransferItem.value = Transfer("")
    }

    fun startTransferDialog() {
        _startTransferDialog.value = true
    }

    fun startTransferDialogComplete() {
        _startTransferDialog.value = false
    }

    fun navToTheBillSupplier(item: BillContact?) {
        _navToTheBillSupplier.value = item!!

    }

    fun deleteBill(billContact: BillContact) {
        viewModelScope.launch {
            billSupplierUseCase.deleteBill(billContact)
        }
    }


}
