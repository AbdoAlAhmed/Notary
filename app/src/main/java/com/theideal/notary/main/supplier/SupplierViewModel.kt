package com.theideal.notary.main.supplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.domain.usecases.SupplierUseCase
import kotlinx.coroutines.launch

class SupplierViewModel(
    private val contactUseCases: ContactUseCases,
    private val supplierUseCase: SupplierUseCase,
) : ViewModel() {

    private val _startSupplierActivity = MutableLiveData<Boolean>()
    val startSupplierActivity: MutableLiveData<Boolean>
        get() = _startSupplierActivity

    private val _snackBar = MutableLiveData<String>()
    val snackBar: MutableLiveData<String>
        get() = _snackBar

    private val _startCompanyActivity = MutableLiveData<Boolean>()
    val startCompanyActivity: MutableLiveData<Boolean>
        get() = _startCompanyActivity

    private val _suppliers = MutableLiveData<List<Contact>>()
    val suppliers: LiveData<List<Contact>>
        get() = _suppliers

    init {
        getSuppliersList()
    }

    fun checkInfoToAddSupplier() {
        viewModelScope.launch {
            try {
                when (contactUseCases.checkUserInfoToCreateContact()) {
                    "NoCompany" -> {
                        _startCompanyActivity.value = true
                        _snackBar.value = "No company"
                    }

                    "NoUser" -> {

                    }

                    else -> {
                        _startSupplierActivity.value = true
                    }
                }

            } catch (e: Exception) {
                _startSupplierActivity.value = false
            }
        }
    }

    fun startSupplierActivityComplete() {
        _startSupplierActivity.value = false
    }

    fun getSuppliersList() {
        viewModelScope.launch {
            try {
                _suppliers.postValue(supplierUseCase.getSuppliers())
            } catch (e: Exception) {
                _snackBar.value = e.message
            }
        }
    }

}