package com.theideal.notary.main.client.theclient.bill

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.data.model.PayBook
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.usecases.BillClientUseCases
import kotlinx.coroutines.launch

class ClientBillViewModel(
    private val billClientUseCases: BillClientUseCases,
    private val clientRepo: ClientRepository,
    private val app: Application,
) :
    ViewModel() {
    private val _dialogTransfer = MutableLiveData<Boolean>()
    val dialogTransfer: LiveData<Boolean>
        get() = _dialogTransfer

    private val _dialogTransaction = MutableLiveData<Boolean>()
    val dialogTransaction: LiveData<Boolean>
        get() = _dialogTransaction

    private val _dialogUpdate = MutableLiveData<Item>()
    val dialogUpdate: LiveData<Item>
        get() = _dialogUpdate

    private val _confirmDeleteDialog = MutableLiveData<String>()
    val confirmDeleteDialog: LiveData<String>
        get() = _confirmDeleteDialog


    private val _confirmDeleteBillDialog = MutableLiveData<Boolean>()
    val confirmDeleteBillDialog: LiveData<Boolean>
        get() = _confirmDeleteBillDialog


    private val _suppliersList = MutableLiveData<List<Contact>>()
    val supperList: LiveData<List<Contact>>
        get() = _suppliersList

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>>
        get() = _items

    private val _permission = MutableLiveData<Boolean>()
    val permission: LiveData<Boolean>
        get() = _permission


    private val _billContact = MutableLiveData<BillContact>()
    val billContact: LiveData<BillContact>
        get() = _billContact

    private val _setBillStatus = MutableLiveData<String>()
    val setBillStatus: LiveData<String>
        get() = _setBillStatus

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar


    fun setSnackBar(message: String) {
        _snackBar.value = message
    }

    fun setSnackComplete() {
        _setBillStatus.value = ""
    }


    fun getSuppliersList() {
        viewModelScope.launch {
            _suppliersList.postValue(
                billClientUseCases.getSuppliersNameFromId()
            )
        }
    }

    fun setTotalToBillContact(billContact: BillContact) {
        viewModelScope.launch {
            if (billContact.billId.isNotEmpty()) {
                val grossMoney = billClientUseCases.totalMoneyItems(billContact.billId)
                val amount = billClientUseCases.totalAmountItems(billContact.billId)
                billContact.grossMoney = grossMoney
                billContact.amount = amount
                _billContact.value = billContact
            }
        }
    }

    fun updateBillContact(billContact: BillContact) {
        _billContact.value = billContact
    }


    fun getContact(contactId: String) {
        viewModelScope.launch {
            _contact.value =
                clientRepo.getClient(contactId)
        }
    }

    fun getItemsByBillId(billId: String) {
        viewModelScope.launch {
            try {
                if (billId.isNotEmpty()) {
                    _items.value = billClientUseCases.getItemsFromBillId(billId)
                }
            } catch (e: Exception) {
                _items.value = emptyList()
            }
        }
    }

    fun updateItem(billId: String, item: Item) {
        viewModelScope.launch {
            billClientUseCases.updateItem(billId, item)
        }
    }

    fun addItemToBillClient(billId: String, item: Item) {
        viewModelScope.launch {
            billClientUseCases.addItemToBillClientWithBillId(billId, item)
        }
    }

    fun deleteItemFromBill(billId: String, itemId: String) {
        viewModelScope.launch {
            billClientUseCases.deleteItemFromBill(billId, itemId)
        }
    }

    fun deleteBill(billId: String) {
        viewModelScope.launch {
            billClientUseCases.deleteBill(billId)
        }
    }

    fun updateBill(billId: String, keyValue: Map<String, Any>) {
        viewModelScope.launch {
            billClientUseCases.updateBill(billId = billId, keyValue)
        }
    }

    fun addPayBook(billId: String, payBook: PayBook) {
        viewModelScope.launch {
            billClientUseCases.addPayBookToBill(billId, payBook)
        }
    }

    fun transferDialogOpen() {
        _dialogTransfer.value = true
    }

    fun transferDialogComplete() {
        _dialogTransfer.value = false
    }

    fun sellDialogOpen() {
        _dialogTransaction.value = true
    }

    fun transactionDialogComplete() {
        _dialogTransaction.value = false
    }

    fun updateDialogOpen(item: Item) {
        _dialogUpdate.value = item
    }

    fun updateDialogComplete() {
        _dialogUpdate.value = Item("")
    }

    fun confirmDeleteDialogOpen(itemId: String) {
        _confirmDeleteDialog.value = itemId
    }

    fun confirmDeleteDialogComplete() {
        _confirmDeleteDialog.value = ""
    }

    fun deleteBillDialogOpen() {
        _confirmDeleteBillDialog.value = true
    }

    fun confirmDeleteBillDialogComplete() {
        _confirmDeleteBillDialog.value = false
    }

    fun checkPermission(context: Context, permission: String) {
        _permission.value = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }


    fun requestPermission(activity: Activity, permission: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permission, requestCode)
    }

    fun permissionGranted() {
        _permission.value = true
    }

    fun permissionDenied() {
        _permission.value = false
    }

    fun setBillStatus(status: String) {
        _setBillStatus.value = status
    }


}