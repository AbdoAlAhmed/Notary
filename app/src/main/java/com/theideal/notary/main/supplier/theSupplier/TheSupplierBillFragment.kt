package com.theideal.notary.main.supplier.theSupplier

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.notary.databinding.DialogSellItemClientBinding
import com.theideal.notary.databinding.FragmentTheSupplierBillBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TheSupplierBillFragment : Fragment() {
    private lateinit var binding: FragmentTheSupplierBillBinding
    private val theSupplierBillViewModel by viewModel<TheSupplierBillViewModel>()
    private val contact = Contact()
    private val item = Item()
    private lateinit var theSupplierBillArgs: TheSupplierBillFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theSupplierBillArgs = TheSupplierBillFragmentArgs.fromBundle(requireArguments())
        theSupplierBillViewModel.getItemsListBySupplierId(theSupplierBillArgs.billContact.contactId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheSupplierBillBinding.inflate(inflater, container, false)
        binding.billContact = theSupplierBillArgs.billContact
        binding.contact = contact
        binding.theSupplierBillViewModel = theSupplierBillViewModel
        theSupplierBillViewModel.setContact(theSupplierBillArgs.contact)
        theSupplierBillViewModel.setBillContact(theSupplierBillArgs.billContact)
        theSupplierBillViewModel.addDialog.observe(viewLifecycleOwner) {
            addItemDialog()
        }
        binding.rvSupplierBill.adapter = TheSupplierBillAdapter()
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun addItemDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val createDialog = alertDialog.create()
        val view = DialogSellItemClientBinding.inflate(layoutInflater)
        view.item = item
        item.supplierName = theSupplierBillViewModel.returnContact().name
        item.supplierId = theSupplierBillViewModel.returnContact().contactId
        view.contact = theSupplierBillViewModel.returnContact()
        view.apply {
            tilSupplierList.isVisible = false
            tvSupplierName.isVisible = true
        }
        view.lifecycleOwner = this
        view.btnSellItem.setOnClickListener {
            theSupplierBillViewModel.addItem(
                theSupplierBillViewModel.returnBillContact().billId,
                item
            )
            createDialog.dismiss()
        }
        createDialog.setView(view.root)
        createDialog.show()
    }

}