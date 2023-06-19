package com.theideal.notary.main.supplier.theSupplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.SupplierUseCase
import kotlinx.coroutines.launch

class CreateSupplierViewModel(private val supplierUseCase: SupplierUseCase) : ViewModel() {

    private val _navToTheSupplier = MutableLiveData<Boolean>()
    val navToTheSupplier: LiveData<Boolean>
        get() = _navToTheSupplier

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    private suspend fun supplierExists(contact: Contact) =
        supplierUseCase.supplierExists(contact.contactId)

    fun createSupplier(contact: Contact) {
        viewModelScope.launch {
            if (supplierExists(contact)) {
                supplierUseCase.createSupplier(contact)
                _navToTheSupplier.postValue(true)
            } else {
                _snackBar.postValue("Supplier already exists")
            }
        }
    }
    fun snackBarComplete() {
        _snackBar.value = ""
    }
}