package com.theideal.notary.main.supplier.theSupplier

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.SuppliersUseCase
import kotlinx.coroutines.launch

class CreateSupplierViewModel(
    private val suppliersUseCase: SuppliersUseCase, private val app: Application
) : ViewModel() {

    private val _navToTheSupplier = MutableLiveData<Boolean>()
    val navToTheSupplier: LiveData<Boolean>
        get() = _navToTheSupplier

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    private suspend fun supplierExists(contact: Contact) =
        suppliersUseCase.supplierPhoneExist(contact.phone)

    fun createSupplier(contact: Contact) {
        viewModelScope.launch {
            if (contact.phone.isEmpty()) {
                suppliersUseCase.createSupplier(contact)
                _navToTheSupplier.value = true
            } else {
                if (supplierExists(contact)) {
                    suppliersUseCase.createSupplier(contact)
                    _navToTheSupplier.value = true
                } else {
                    _snackBar.value = app.getString(com.theideal.notary.R.string.phone_number_taken)
                }
            }

        }
    }

    fun snackBarComplete() {
        _snackBar.value = ""
    }
}