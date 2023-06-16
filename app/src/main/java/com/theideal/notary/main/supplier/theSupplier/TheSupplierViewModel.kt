package com.theideal.notary.main.supplier.theSupplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.domain.usecases.CreateBillSupplierUseCases
import com.theideal.domain.usecases.SupplierUseCase
import kotlinx.coroutines.launch

class TheSupplierViewModel(
    private val supplierUseCase: SupplierUseCase,
    private val createBillUseCase: CreateBillSupplierUseCases,
) : ViewModel() {


    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _navToSupplierBill = MutableLiveData<Boolean>()
    val navToSupplierBill: LiveData<Boolean>
        get() = _navToSupplierBill

    private val _billList = MutableLiveData<List<BillContact>>()
    val billList: LiveData<List<BillContact>>
        get() = _billList


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

        fun setSupplier(contact: Contact) {
            _contact.value = contact
        }

        fun createBillSupplier(contact: Contact) {
            viewModelScope.launch {
                createBillUseCase.createBill(BillContact(), contact)
                _navToSupplierBill.value = true
            }
        }

        fun navToSupplierBillComplete() {
            _navToSupplierBill.value = false
        }
    }
