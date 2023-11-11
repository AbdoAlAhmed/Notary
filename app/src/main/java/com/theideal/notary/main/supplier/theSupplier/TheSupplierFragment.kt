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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Transfer
import com.theideal.notary.R
import com.theideal.notary.databinding.DeleteDialogWithCondiationBinding
import com.theideal.notary.databinding.DialogEditBinding
import com.theideal.notary.databinding.DialogTransferTheClientBinding
import com.theideal.notary.databinding.FragmentTheSupplierBinding
import com.theideal.notary.utils.SwipeCallBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class TheSupplierFragment : Fragment() {
    private val theSupplierViewModel by viewModel<TheSupplierViewModel>()
    private val supplierAndTheSupplierViewModel by viewModel<SupplierAndTheSupplierViewModel>()
    private lateinit var binding: FragmentTheSupplierBinding
    private lateinit var args: TheSupplierFragmentArgs
    private var billContact = BillContact()
    private var contact = Contact()
    private var transfer = Transfer()
    private lateinit var theSupplierAdapterTransfer: TheSupplierAdapterTransfer
    private lateinit var theSupplierAdapter: TheSupplierAdapter
    private var emptyContact = Contact()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        args = TheSupplierFragmentArgs.fromBundle(requireArguments())
        val intent = requireActivity().intent.getStringExtra("contactId")
        intent?.let {
            theSupplierViewModel.getSupplier(it)
            theSupplierViewModel.getTotalMoney(it)
            contact.contactId = it
        }
        args.contact?.let {
            theSupplierViewModel.setSupplier(it)
            theSupplierViewModel.getTotalMoney(it.contactId)
            contact = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheSupplierBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarTheSupplier)
        binding.theSupplierViewModel = theSupplierViewModel
        theSupplierViewModel.billContact.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.billContact = it
                billContact = it
            }
        }
        theSupplierViewModel.contact.observe(viewLifecycleOwner) {
            if (it != null) {
                contact = it
                binding.contact = it
            }
        }
        binding.lifecycleOwner = this
        theSupplierAdapter =
            TheSupplierAdapter(theSupplierViewModel, TheSupplierAdapter.OnClick {
                findNavController().navigate(
                    TheSupplierFragmentDirections.actionTheSupplierFragmentToTheSupplierBillFragment(
                        it, contact
                    )
                )
            })
        binding.rvBillsTheSupplier.adapter = theSupplierAdapter
        val swipeCallBackBill = SwipeCallBack(theSupplierAdapter)
        val itemTouchHelperBill = ItemTouchHelper(swipeCallBackBill)
        itemTouchHelperBill.attachToRecyclerView(binding.rvBillsTheSupplier)
        theSupplierAdapterTransfer =
            TheSupplierAdapterTransfer(theSupplierViewModel, TheSupplierAdapterTransfer.OnClick {
                dialogTransferEdit(it)
            })
        binding.rvTheClientTransfer.adapter = theSupplierAdapterTransfer
        val swipeCallBack = SwipeCallBack(theSupplierAdapterTransfer)
        val itemTouchHelper = ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvTheClientTransfer)

        theSupplierViewModel.navToTheBillSupplier.observe(viewLifecycleOwner) {
            if (it.status != "") {
                findNavController().navigate(
                    TheSupplierFragmentDirections.actionTheSupplierFragmentToTheSupplierBillFragment(
                        billContact, contact
                    )
                )
                theSupplierViewModel.navToTheSupplierBillComplete()
            }
        }
        theSupplierViewModel.startTransferDialog.observe(viewLifecycleOwner) {
            if (it) {
                dialogTransfer()
            }
        }

        theSupplierViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                theSupplierViewModel.snackBarComplete()
            }
        }

        theSupplierViewModel.dialogEditTransferItem.observe(viewLifecycleOwner) {
            if (it.transferId != "") {
                dialogTransferEdit(it)
            }
        }

        theSupplierViewModel.dialogDeleteTransferItem.observe(viewLifecycleOwner) {
            if (it.transferId != "") {
                confirmDeleteItemTransfer(it)
            }
        }
        theSupplierViewModel.billContact.observe(viewLifecycleOwner) {
            if (it.billId != "") {
                billContact = it
            }
        }

        theSupplierViewModel.dialogDeleteBill.observe(viewLifecycleOwner) {
            if (it.status != "") {
                confirmDeleteBill(it)
            }
        }


        return binding.root
    }

    private fun confirmDeleteBill(billContact: BillContact?) {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        dialogCreate.setTitle(getString(R.string.delete))
        dialogCreate.setMessage(getString(R.string.confirm_delete_item))
        dialogCreate.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.sure)
        ) { dialog, which ->
            theSupplierViewModel.deleteBill(billContact!!)
            theSupplierAdapter.removeItem(billContact.billId)
            theSupplierViewModel.deleteDialogTransferComplete()
            dialog.dismiss()
        }
        dialogCreate.show()
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
                dialogCreate.dismiss()
            } else {
                dialogCreate.dismiss()
                supplierAndTheSupplierViewModel.startSnackBar(getString(R.string.contact_didnt_deleted))
            }
        }
        dialogCreate.show()
    }


    private fun dialogTransfer() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogTransferTheClientBinding.inflate(layoutInflater)
        view.radioGroupTransfer.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                view.radioBtnDeposit.id -> {
                    theSupplierViewModel.setTypeOfFinancialTransfer(resources.getString(R.string.deposit))
                }

                view.radioBtnWithdraw.id -> {
                    theSupplierViewModel.setTypeOfFinancialTransfer(resources.getString(R.string.withdraw))
                }
            }
        }
        view.transfer = transfer
        dialogCreate.setView(view.root)
        dialogCreate.show()
        view.btnTransfer.setOnClickListener {
            transfer.contactId = contact.contactId
            theSupplierViewModel.addTransfer(transfer = transfer)
            theSupplierAdapterTransfer.addItem(transfer)
            theSupplierViewModel.getTotalMoney(contact.contactId)
            dialogCreate.dismiss()
            theSupplierViewModel.startTransferDialogComplete()
        }

    }

    private fun dialogTransferEdit(transfer: Transfer) {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogTransferTheClientBinding.inflate(layoutInflater)
        view.btnTransfer.apply {
            text = getString(R.string.edit)
            setBackgroundColor(resources.getColor(R.color.dark_ocean_blue))
        }
        view.radioGroupTransfer.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                view.radioBtnDeposit.id -> {
                    theSupplierViewModel.setTypeOfFinancialTransfer(resources.getString(R.string.deposit))
                }

                view.radioBtnWithdraw.id -> {
                    theSupplierViewModel.setTypeOfFinancialTransfer(resources.getString(R.string.withdraw))
                }
            }
        }
        view.transfer = transfer
        dialogCreate.setView(view.root)
        dialogCreate.show()
        theSupplierViewModel.editDialogComplete()
        view.btnTransfer.setOnClickListener {
            transfer.contactId = contact.contactId

            theSupplierViewModel.updateTransfer(transfer = transfer)
            theSupplierAdapterTransfer.updateItemAt(transfer)
            dialogCreate.dismiss()
            theSupplierViewModel.startTransferDialogComplete()

        }

    }

    private fun confirmDeleteItemTransfer(transfer: Transfer) {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        dialogCreate.setTitle(getString(R.string.delete))
        dialogCreate.setMessage(getString(R.string.confirm_delete_item))
        dialogCreate.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.sure)
        ) { dialog, which ->
            theSupplierViewModel.deleteTransfer(transfer)
            theSupplierAdapterTransfer.removeItemAt(transfer)
            dialog.dismiss()
        }
        theSupplierViewModel.deleteDialogTransferComplete()
        dialogCreate.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_the_with_no_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                editContactDialog(contact)
            }

            R.id.action_delete -> {
                deleteContactDialog(contact)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onStart() {
        theSupplierViewModel.getBillBySupplierId(contact.contactId)
        theSupplierViewModel.getTransfersWithContactId(contact.contactId)
        super.onStart()
    }
}