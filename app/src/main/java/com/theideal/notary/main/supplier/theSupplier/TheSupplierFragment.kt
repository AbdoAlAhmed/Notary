package com.theideal.notary.main.supplier.theSupplier

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Transfer
import com.theideal.notary.R
import com.theideal.notary.databinding.DialogTransferTheClientBinding
import com.theideal.notary.databinding.FragmentTheSupplierBinding
import com.theideal.notary.utils.SwipeCallBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class TheSupplierFragment : Fragment() {
    private val theSupplierViewModel by viewModel<TheSupplierViewModel>()
    private lateinit var binding: FragmentTheSupplierBinding
    private lateinit var args: TheSupplierFragmentArgs
    private var billContact = BillContact()
    private var contact = Contact()
    private var transfer = Transfer()
    private lateinit var theSupplierAdapterTransfer: TheSupplierAdapterTransfer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = TheSupplierFragmentArgs.fromBundle(requireArguments())
        theSupplierViewModel.getTransfersWithContactId(args.contact.contactId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheSupplierBinding.inflate(inflater, container, false)
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
        binding.rvBillsTheSupplier.adapter = BillsSupplierAdapter(BillsSupplierAdapter.OnClick {
            findNavController().navigate(
                TheSupplierFragmentDirections.actionTheSupplierFragmentToTheSupplierBillFragment(
                    it
                )
            )
        })
        theSupplierAdapterTransfer =
            TheSupplierAdapterTransfer(theSupplierViewModel, TheSupplierAdapterTransfer.OnClick {

            })
        binding.rvTheClientTransfer.adapter = theSupplierAdapterTransfer
        val swipeCallBack = SwipeCallBack(theSupplierAdapterTransfer)
        val itemTouchHelper = ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvTheClientTransfer)
        val intent = requireActivity().intent.getStringExtra("contactId")
        if (intent != null) {
            theSupplierViewModel.getSupplier(intent)
            theSupplierViewModel.getBillBySupplierId(intent)
            theSupplierViewModel.getTransfersWithContactId(intent)
        } else {
            theSupplierViewModel.setSupplier(args.contact)
            theSupplierViewModel.getBillBySupplierId(args.contact.contactId)
        }

        theSupplierViewModel.navToSupplierBill.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    TheSupplierFragmentDirections.actionTheSupplierFragmentToTheSupplierBillFragment(
                        billContact
                    )
                )
                theSupplierViewModel.navToSupplierBillComplete()
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




        return binding.root
    }

    private fun dialogTransfer() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogTransferTheClientBinding.inflate(layoutInflater)
        view.radioGroupTransfer.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                view.radioBtnDeposit.id -> {
                    theSupplierViewModel.setTypeOfFinancialTransfer("DEPOSIT")
                }

                view.radioBtnWithdraw.id -> {
                    theSupplierViewModel.setTypeOfFinancialTransfer("WITHDRAW")
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
                    theSupplierViewModel.setTypeOfFinancialTransfer("DEPOSIT")
                }

                view.radioBtnWithdraw.id -> {
                    theSupplierViewModel.setTypeOfFinancialTransfer("WITHDRAW")
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
            AlertDialog.BUTTON_POSITIVE,
            getString(R.string.sure)
        ) { dialog, which ->
            theSupplierViewModel.deleteTransfer(transfer)
            theSupplierAdapterTransfer.removeItemAt(transfer)
            dialog.dismiss()
        }
        theSupplierViewModel.deleteDialogComplete()
        dialogCreate.show()
    }


}