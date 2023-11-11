package com.theideal.notary.main.supplier.theSupplier

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.SuppliersUseCase
import kotlinx.coroutines.launch

class SupplierAndTheSupplierViewModel(private val suppliersUseCase: SuppliersUseCase) :
    ViewModel() {

    private val _editSupplierDialog = MutableLiveData<Contact>()
    val editSupplierDialog: MutableLiveData<Contact>
        get() = _editSupplierDialog

    private val _deleteSupplierDialog = MutableLiveData<Contact>()
    val deleteSupplierDialog: MutableLiveData<Contact>
        get() = _deleteSupplierDialog

    private val _snackBar = MutableLiveData<String>()
    val snackBar: MutableLiveData<String>
        get() = _snackBar


    fun editSupplierDialog(contact: Contact) {
        _editSupplierDialog.value = contact
    }

    fun deleteSupplierDialog(contact: Contact) {
        _deleteSupplierDialog.value = contact
    }

    fun editSupplierDialogComplete() {
        _editSupplierDialog.value = Contact("")
    }

    fun deleteSupplierDialogComplete() {
        _deleteSupplierDialog.value = Contact("")
    }

    fun startSnackBar(message: String) {
        _snackBar.value = message
    }

    fun snackBarComplete() {
        _snackBar.value = ""
    }

    fun updateContactPhone(contact: Contact, keyValue: Map<String, Any>) {
        viewModelScope.launch {
            if (suppliersUseCase.supplierPhoneExist(contact.phone)) {
                suppliersUseCase.updateSupplier(contact, keyValue)
            }
        }
    }

    fun updateContactName(contact: Contact, keyValue: Map<String, Any>) {
        viewModelScope.launch {
            suppliersUseCase.updateSupplier(contact, keyValue)
        }
    }

    fun deleteSupplier(contact: Contact) {
        viewModelScope.launch {
            suppliersUseCase.deleteSupplier(contact)
        }
    }
}