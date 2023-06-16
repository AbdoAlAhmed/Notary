package com.theideal.notary.main.client.createclient

import android.app.AlertDialog
import android.content.Intent
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
import com.theideal.notary.databinding.DialogTransferTheClientBinding
import com.theideal.notary.databinding.FragmentTheClientBinding
import com.theideal.notary.main.MainActivity
import com.theideal.notary.utils.SwipeCallBack
import org.koin.androidx.viewmodel.ext.android.viewModel


class TheClientFragment : Fragment() {
    private lateinit var binding: FragmentTheClientBinding
    private val theClientViewModel by viewModel<TheClientViewModel>()
    private var billContact = BillContact()
    private val transfer = Transfer()
    private var contact = Contact()
    private lateinit var args: TheClientFragmentArgs
    private lateinit var adapterTransfer: TheClientAdapterTransfer
    private lateinit var adapterTransactions: TheClientAdapterTransactions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = TheClientFragmentArgs.fromBundle(requireArguments())
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheClientBinding.inflate(inflater)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarTheClient)
        binding.theClientViewModel = theClientViewModel
        theClientViewModel.billContact.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.billContact = it
                billContact = it
            }
        }
        theClientViewModel.contact.observe(viewLifecycleOwner) {
            if (it != null) {
                contact = it
                binding.contact = it
            }
        }

        val contact =
            requireActivity().intent.getStringExtra("contactId") // get contactId from SaleTransactionsFragment
        if (contact != null) {
            theClientViewModel.getClient(contact)
            theClientViewModel.getBillsByContactId(contact)
            theClientViewModel.getTransfersWithContactId(contact)

        } else {
            theClientViewModel.setClient(args.contact!!)
            theClientViewModel.getTransfersWithContactId(args.contact.phone)
            theClientViewModel.getBillsByContactId(args.contact.phone)
        }
        adapterTransactions =
            TheClientAdapterTransactions(theClientViewModel, TheClientAdapterTransactions.OnClick {
                findNavController().navigate(
                    TheClientFragmentDirections.actionTheClientFragmentToClientBillFragment(
                        it
                    )
                )
            })

        binding.rvTheClientTransactions.adapter = adapterTransactions
        val swipeCallBack = SwipeCallBack(adapterTransactions)
        val itemTouchHelper = ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvTheClientTransactions)

        adapterTransfer =
            TheClientAdapterTransfer(theClientViewModel,
                TheClientAdapterTransfer.OnClick {

                }
            )

        binding.rvTheClientTransfer.adapter = adapterTransfer
        val swipeCallBackTransfer = SwipeCallBack(adapterTransfer)
        val itemTouchHelperTransfer = ItemTouchHelper(swipeCallBackTransfer)
        itemTouchHelperTransfer.attachToRecyclerView(binding.rvTheClientTransfer)

        theClientViewModel.navToClientBill.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    TheClientFragmentDirections.actionTheClientFragmentToClientBillFragment(
                        billContact
                    )
                )
                theClientViewModel.navToClientBillComplete()
            }
        }

        theClientViewModel.startTransferDialog.observe(viewLifecycleOwner) {
            if (it) {
                dialogTransfer()
            }
        }

        theClientViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                theClientViewModel.snackBarComplete()
            }
        }

        theClientViewModel.dialogEditTransferItem.observe(viewLifecycleOwner) {
            if (it.transferId != "") {
                dialogTransferEdit(it)
            }
        }
        theClientViewModel.dialogDeleteTransferItem.observe(viewLifecycleOwner) {
            if (it.transferId != "") {
                confirmDeleteItemTransfer(it)
            }
        }
        theClientViewModel.navToClientBillWithBillContact.observe(viewLifecycleOwner) {
            if (it.status != "") {
                findNavController().navigate(
                    TheClientFragmentDirections.actionTheClientFragmentToClientBillFragment(
                        it
                    )
                )
                theClientViewModel.navToClientBillWithBillContactComplete()
            }
        }
        theClientViewModel.deleteBillConfirmDialog.observe(viewLifecycleOwner) {
            if (it.status != "") {
                confirmDeleteItemBill(it)
            }
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun dialogTransfer() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogTransferTheClientBinding.inflate(layoutInflater)
        view.radioGroupTransfer.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                view.radioBtnDeposit.id -> {
                    theClientViewModel.setTypeOfFinancialTransfer("DEPOSIT")
                }

                view.radioBtnWithdraw.id -> {
                    theClientViewModel.setTypeOfFinancialTransfer("WITHDRAW")
                }
            }
        }
        view.transfer = transfer
        dialogCreate.setView(view.root)
        dialogCreate.show()
        view.btnTransfer.setOnClickListener {
            transfer.contactId = contact.contactId
            theClientViewModel.addTransfer(transfer = transfer)
            adapterTransfer.addItem(transfer)
            dialogCreate.dismiss()
            theClientViewModel.startTransferDialogComplete()
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
                    theClientViewModel.setTypeOfFinancialTransfer("DEPOSIT")
                }

                view.radioBtnWithdraw.id -> {
                    theClientViewModel.setTypeOfFinancialTransfer("WITHDRAW")
                }
            }
        }
        view.transfer = transfer
        dialogCreate.setView(view.root)
        dialogCreate.show()
        theClientViewModel.editDialogComplete()
        view.btnTransfer.setOnClickListener {
            transfer.contactId = contact.contactId

            theClientViewModel.updateTransfer(transfer = transfer)
            adapterTransfer.updateItemAt(transfer)
            dialogCreate.dismiss()
            theClientViewModel.startTransferDialogComplete()

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
            theClientViewModel.deleteTransfer(transfer)
            adapterTransfer.removeItemAt(transfer)
            dialog.dismiss()
        }
        theClientViewModel.deleteDialogComplete()
        dialogCreate.show()
    }

    private fun confirmDeleteItemBill(billContact: BillContact) {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        dialogCreate.setTitle(getString(R.string.delete))
        dialogCreate.setMessage(getString(R.string.confirm_delete_item))
        dialogCreate.setButton(
            AlertDialog.BUTTON_POSITIVE,
            getString(R.string.sure)
        ) { dialog, which ->
            theClientViewModel.deleteBill(billContact)
            adapterTransactions.removeItemAt(billContact)
            dialog.dismiss()
        }
        theClientViewModel.deleteBillConfirmDialogComplete()
        dialogCreate.show()
    }

    private fun dialogDeleteContact() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        dialogCreate.setTitle(getString(R.string.delete))
        dialogCreate.setMessage(getString(R.string.confirm_delete_contact))
        dialogCreate.setButton(
            AlertDialog.BUTTON_POSITIVE,
            getString(R.string.sure)
        ) { dialog, which ->
            theClientViewModel.deleteContact(contact)
            startActivity(
                Intent(requireContext(), MainActivity::class.java)
            )
            dialog.dismiss()
        }
        dialogCreate.show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_the_contact, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                dialogDeleteContact()
            }

            R.id.action_print -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}