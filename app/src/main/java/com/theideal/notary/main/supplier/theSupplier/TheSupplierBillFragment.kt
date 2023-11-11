package com.theideal.notary.main.supplier.theSupplier

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.notary.R
import com.theideal.notary.databinding.DialogSellItemClientBinding
import com.theideal.notary.databinding.FragmentTheSupplierBillBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TheSupplierBillFragment : Fragment() {
    private lateinit var binding: FragmentTheSupplierBillBinding
    private val theSupplierBillViewModel by viewModel<TheSupplierBillViewModel>()
    private var contact = Contact()
    private var billContact = BillContact()
    private val item = Item()
    private lateinit var theSupplierBillArgs: TheSupplierBillFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theSupplierBillArgs = TheSupplierBillFragmentArgs.fromBundle(requireArguments())
        theSupplierBillArgs.billContact.let {
            billContact = it
            theSupplierBillViewModel.setBillContact(it)
        }
        theSupplierBillArgs.contact.let {
            contact = it
            theSupplierBillViewModel.getItemsListBySupplierId(it.contactId)
        }
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheSupplierBillBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarSupplierBill)
        binding.billContact = billContact
        binding.contact = contact
        binding.theSupplierBillViewModel = theSupplierBillViewModel
        theSupplierBillViewModel.setBillContact(theSupplierBillArgs.billContact)
        theSupplierBillViewModel.addDialog.observe(viewLifecycleOwner) {
            addItemDialog()
            theSupplierBillViewModel.snackBarMessage(resources.getString(R.string.add_item_from_the_bill_supplier))
        }
        theSupplierBillViewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (it != ""){
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                theSupplierBillViewModel.snackBarMessageComplete()
            }
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
        view.contact = contact
        item.supplierName = contact.name
        item.supplierId = contact.contactId
        view.apply {
            tilSupplierList.isVisible = false
            tvSupplierName.isVisible = true
        }
        view.lifecycleOwner = this
        view.btnSellItem.setOnClickListener {
            theSupplierBillViewModel.addItem(
                billContact.billId,
                item
            )
            createDialog.dismiss()
        }
        createDialog.setView(view.root)
        createDialog.show()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.toolbar_the_contact, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_delete -> {
//            }
//
//            R.id.action_edit -> {
//            }
//
//            R.id.action_share -> {
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}