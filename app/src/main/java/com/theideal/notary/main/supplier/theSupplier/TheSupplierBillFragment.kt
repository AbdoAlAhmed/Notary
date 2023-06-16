package com.theideal.notary.main.supplier.theSupplier

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.notary.databinding.DialogAddItemSupplierBinding
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
        theSupplierBillViewModel.addDialog.observe(viewLifecycleOwner) {
            if (it) {
                addItemDialog()
                theSupplierBillViewModel.addDialogComplete()
            }
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun addItemDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        val dialogCreate = dialog.create()
        val dialogView = DialogAddItemSupplierBinding.inflate(layoutInflater, null, false)
        dialogView.item = item
        dialogCreate.setView(dialogView.root)
        dialogView.btnAddItemSupplier.setOnClickListener {
            theSupplierBillViewModel.addItem( theSupplierBillArgs.billContact.billId,item)
            dialogCreate.dismiss()
        }
        dialogCreate.show()
    }

}