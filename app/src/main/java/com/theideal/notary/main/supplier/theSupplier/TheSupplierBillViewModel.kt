package com.theideal.notary.main.supplier.theSupplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.domain.usecases.BillSupplierUseCases
import kotlinx.coroutines.launch

class TheSupplierBillViewModel(private val billSupplierUseCases: BillSupplierUseCases) :
    ViewModel() {

    private val _addDialog = MutableLiveData<Boolean>()
    val addDialog: LiveData<Boolean>
        get() = _addDialog

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _billContact = MutableLiveData<BillContact>()
    val billContact: LiveData<BillContact>
        get() = _billContact

    private val _itemsList = MutableLiveData<List<Item>>()
    val itemsList: LiveData<List<Item>>
        get() = _itemsList


    fun setContact(contact: Contact) {
        _contact.value = contact
    }

    fun returnContact(): Contact {
        return _contact.value!!
    }

    fun setBillContact(billContact: BillContact) {
        _billContact.value = billContact
    }

    fun returnBillContact(): BillContact {
        return _billContact.value!!
    }

    fun addItem(billId: String, item: Item) {
        viewModelScope.launch {
            billSupplierUseCases.addItemToBill(billId, item)
        }
    }

    fun getItemsListBySupplierId(supplierId: String) =
        viewModelScope.launch {
            _itemsList.value = billSupplierUseCases.calculateTheBillForSupplier(supplierId)
        }

    fun addDialog() {
        _addDialog.value = true
    }

    fun addDialogComplete() {
        _addDialog.value = false
    }
}