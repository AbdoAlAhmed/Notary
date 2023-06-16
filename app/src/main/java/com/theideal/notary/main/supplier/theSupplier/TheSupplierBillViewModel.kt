package com.theideal.notary.main.supplier.theSupplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.data.model.Item
import com.theideal.domain.usecases.CreateBillSupplierUseCases
import kotlinx.coroutines.launch

class TheSupplierBillViewModel(private val billSupplierUseCases: CreateBillSupplierUseCases) : ViewModel() {

    private val _addDialog = MutableLiveData<Boolean>()
    val addDialog: LiveData<Boolean>
        get() = _addDialog


    fun addItem ( billId: String,item: Item){
        viewModelScope.launch {
            billSupplierUseCases.addItemToBillAndUpdateBillInfo(billId, item)
        }
    }

    fun addDialog() {
        _addDialog.value = true
    }

    fun addDialogComplete() {
        _addDialog.value = false
    }
}