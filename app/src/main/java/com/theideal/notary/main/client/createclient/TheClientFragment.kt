package com.theideal.notary.main.client.createclient

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Transfer
import com.theideal.notary.databinding.DialogTransferTheClientBinding
import com.theideal.notary.databinding.FragmentTheClientBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TheClientFragment : Fragment() {
    private lateinit var binding: FragmentTheClientBinding
    private val theClientViewModel by viewModel<TheClientViewModel>()
    private var billContact = BillContact()
    private val transfer = Transfer()
    private var contact = Contact()
    private lateinit var args: TheClientFragmentArgs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = TheClientFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheClientBinding.inflate(inflater)
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


        binding.rvTheClientTransactions.adapter =
            TheClientAdapterTransactions(TheClientAdapterTransactions.OnClick {
                findNavController().navigate(
                    TheClientFragmentDirections.actionTheClientFragmentToClientBillFragment(
                        it
                    )
                )
            })
        binding.rvTheClientTransfer.adapter =
            TheClientAdapterTransfer(TheClientAdapterTransfer.OnClick {
            })


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
            dialogCreate.dismiss()
            theClientViewModel.startTransferDialogComplete()
        }

    }


}