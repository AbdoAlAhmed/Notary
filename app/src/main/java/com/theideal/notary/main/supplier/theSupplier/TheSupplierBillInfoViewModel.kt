package com.theideal.notary.main.supplier.theSupplier

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.data.model.ItemInfo
import com.theideal.domain.usecases.CreateBillSupplierUseCases
import kotlinx.coroutines.launch

class TheSupplierBillInfoViewModel(private val createBillSupplier: CreateBillSupplierUseCases) :
    ViewModel() {

    private val _addBillInfoDialog = MutableLiveData<Boolean>()
    val addBillInfoDialog: MutableLiveData<Boolean>
        get() = _addBillInfoDialog

    private val _contact = MutableLiveData<Contact>()
    val contact: MutableLiveData<Contact>
        get() = _contact

    private val _listOfBillInfo = MutableLiveData<List<ItemInfo>>()
    val listOfBillInfo: MutableLiveData<List<ItemInfo>>
        get() = _listOfBillInfo

    private val _navToTheSupplierBill = MutableLiveData<Boolean>()
    val navToTheSupplierBill: MutableLiveData<Boolean>
        get() = _navToTheSupplierBill

    private val _totalAmount = MutableLiveData<Double>()
    val totalAmount: MutableLiveData<Double>
        get() = _totalAmount


    fun setContact(contact: Contact) {
        _contact.value = contact
    }

    fun returnContact(): Contact {
        return _contact.value!!
    }

    fun addBillInfo(billId: String, ItemInfo: ItemInfo) {
        viewModelScope.launch {
            createBillSupplier.addItemBillSupplierInfoWithBillId(billId, ItemInfo)
        }
    }

    fun getListOfBillInfo(billId: String) {
        viewModelScope.launch {
            _listOfBillInfo.value = createBillSupplier.getListOfItemsBillInfo(billId)
        }
    }

    fun setTotalAmount(billId: String) {
        viewModelScope.launch {
            _totalAmount.value = createBillSupplier.getTotalAmountOfBill(billId)
        }
    }

    fun addBillInfoDialog() {
        _addBillInfoDialog.value = true
    }

    fun addBillInfoDialogComplete() {
        _addBillInfoDialog.value = false
    }

    fun navToTheSupplierBill() {
        _navToTheSupplierBill.value = true
    }

    fun navToTheSupplierBillComplete() {
        _navToTheSupplierBill.value = false
    }
}