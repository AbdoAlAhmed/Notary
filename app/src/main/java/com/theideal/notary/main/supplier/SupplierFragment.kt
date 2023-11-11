package com.theideal.notary.main.supplier

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.Contact
import com.theideal.notary.R
import com.theideal.notary.databinding.DeleteDialogWithCondiationBinding
import com.theideal.notary.databinding.DialogEditBinding
import com.theideal.notary.databinding.FragmentSupplierBinding
import com.theideal.notary.main.company.CompanyActivity
import com.theideal.notary.main.supplier.theSupplier.SupplierActivity
import com.theideal.notary.main.supplier.theSupplier.SupplierAdapter
import com.theideal.notary.main.supplier.theSupplier.SupplierAndTheSupplierViewModel
import com.theideal.notary.utils.SwipeCallBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupplierFragment : Fragment() {
    private lateinit var binding: FragmentSupplierBinding
    private val supplierViewModel by viewModel<SupplierViewModel>()
    private val supplierAndTheSupplierViewModel by viewModel<SupplierAndTheSupplierViewModel>()
    private lateinit var suppliersAdapter: SupplierAdapter
    private var contact = Contact()
    private var emptyContact = Contact()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supplierViewModel.getSuppliersList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSupplierBinding.inflate(inflater, container, false)
        binding.supplierViewModel = supplierViewModel
        binding.contact = contact
        binding.lifecycleOwner = this
        suppliersAdapter =
            SupplierAdapter(supplierAndTheSupplierViewModel, SupplierAdapter.OnClick {
                val intent = Intent(activity, SupplierActivity::class.java).apply {
                    putExtra("fragment", "TheSupplierFragment")
                    putExtra("contactId", it.contactId)
                }
                startActivity(intent)
            })
        binding.rvSuppliers.adapter = suppliersAdapter
        val swipeCallBack = SwipeCallBack(suppliersAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvSuppliers)

        supplierViewModel.startSupplierActivity.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(activity, SupplierActivity::class.java)
                startActivity(intent)
                supplierViewModel.startSupplierActivityComplete()
            }
        }
        supplierViewModel.startCompanyActivity.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(activity, CompanyActivity::class.java)
                startActivity(intent)
            }
        }
        supplierViewModel.moveToTheClientFromSearch.observe(viewLifecycleOwner) {
            if (it.contactId != "") {
                moveToTheClientFromSearch(it)
            }
        }
        supplierAndTheSupplierViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                supplierAndTheSupplierViewModel.snackBarComplete()
            }
        }

        supplierAndTheSupplierViewModel.deleteSupplierDialog.observe(viewLifecycleOwner) {
            if (it.contactId != "") {
                deleteContactDialog(it)
                supplierAndTheSupplierViewModel.deleteSupplierDialogComplete()
            }
        }
        supplierAndTheSupplierViewModel.editSupplierDialog.observe(viewLifecycleOwner) {
            if (it.contactId != "") {
                editContactDialog(it)
                supplierAndTheSupplierViewModel.editSupplierDialogComplete()
            }
        }

        return binding.root
    }

    private fun moveToTheClientFromSearch(contact: Contact) {
        val intent = Intent(activity, SupplierActivity::class.java).apply {
            putExtra("fragment", "TheSupplierFragment")
            putExtra("contactId", contact.contactId)
        }
        startActivity(intent)
        supplierViewModel.moveToTheClientFromSearchDailyCompleted()
    }

    private fun editContactDialog(contact: Contact) {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialog = dialogBuilder.create()
        val view = DialogEditBinding.inflate(layoutInflater)
        view.contact = contact
        dialog.setView(view.root)
        view.btnEditClient.setOnClickListener {
            val name = mapOf("name" to contact.name)
            val phone = mapOf("phone" to contact.phone)
            supplierAndTheSupplierViewModel.updateContactName(contact, name)
            supplierAndTheSupplierViewModel.updateContactPhone(contact, phone)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteContactDialog(contact2: Contact) {
        val dialogAlert = AlertDialog.Builder(context)
        val dialogCreate = dialogAlert.create()
        val view = DeleteDialogWithCondiationBinding.inflate(layoutInflater)
        view.contact = emptyContact
        view.lifecycleOwner = this
        dialogCreate.setView(view.root)
        view.btnSureToDeleteContact.setOnClickListener {
            if (emptyContact.name == contact2.name) {
                supplierAndTheSupplierViewModel.deleteSupplier(contact2)
                supplierAndTheSupplierViewModel.startSnackBar(getString(R.string.supplier_deleted))
                supplierViewModel.getSuppliersList()
                dialogCreate.dismiss()
            } else {
                dialogCreate.dismiss()
                supplierAndTheSupplierViewModel.startSnackBar(getString(R.string.contact_didnt_deleted))
            }
        }
        dialogCreate.show()
    }


}