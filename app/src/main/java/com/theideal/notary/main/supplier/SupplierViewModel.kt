package com.theideal.notary.main.supplier

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.ContactUseCases
import com.theideal.domain.usecases.SuppliersUseCase
import com.theideal.notary.R
import kotlinx.coroutines.launch

class SupplierViewModel(
    private val contactUseCases: ContactUseCases,
    private val suppliersUseCase: SuppliersUseCase,
    private val app: Application
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

    private val _moveToTheClientFromSearch = MutableLiveData<Contact>()
    val moveToTheClientFromSearch: LiveData<Contact>
        get() = _moveToTheClientFromSearch

    init {
        getSuppliersList()
    }

    fun checkInfoToAddSupplier() {
        viewModelScope.launch {
            try {
                when (contactUseCases.checkUserInfoToCreateContact()) {
                    "NoCompany" -> {
                        _startCompanyActivity.value = true
                        _snackBar.value = app.getString(R.string.create_company_error)
                    }

                    "NoUser" -> {
                        _snackBar.value = app.getString(R.string.no_user)
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
                _suppliers.value = suppliersUseCase.getSuppliers()
            } catch (e: Exception) {
                _snackBar.value = e.message
            }
        }
    }

    fun moveToTheSupplierFromSearch(contact: Contact){
        _moveToTheClientFromSearch.value = contact
    }

    fun moveToTheClientFromSearchDailyCompleted() {
        _moveToTheClientFromSearch.value!!.contactId = ""
    }

}