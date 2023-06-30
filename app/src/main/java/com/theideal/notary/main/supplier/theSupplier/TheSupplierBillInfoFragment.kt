package com.theideal.notary.main.supplier.theSupplier

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.theideal.data.model.ItemInfo
import com.theideal.notary.databinding.DialogAddItemSupplierBinding
import com.theideal.notary.databinding.FragmentTheSupplierBillInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TheSupplierBillInfoFragment : Fragment() {
    private lateinit var binding: FragmentTheSupplierBillInfoBinding
    private val theSupplierBillInfoViewModel by viewModel<TheSupplierBillInfoViewModel>()
    private lateinit var billInfoArgs: TheSupplierBillInfoFragmentArgs
    private lateinit var billInfoAdapter: TheSupplierBillInfoAdapter
    private val itemInfo = ItemInfo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        billInfoArgs = TheSupplierBillInfoFragmentArgs.fromBundle(requireArguments())
        theSupplierBillInfoViewModel.getListOfBillInfo(billInfoArgs.billContact.billId)
        theSupplierBillInfoViewModel.setTotalAmount(billInfoArgs.billContact.billId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheSupplierBillInfoBinding.inflate(inflater, container, false)
        binding.billContact = billInfoArgs.billContact
        binding.theSupplierBillInfoViewModel = theSupplierBillInfoViewModel
        binding.lifecycleOwner = this
        billInfoAdapter = TheSupplierBillInfoAdapter()
        binding.rvSupplierBillInfo.adapter = billInfoAdapter
        theSupplierBillInfoViewModel.setContact(billInfoArgs.contact)
        theSupplierBillInfoViewModel.addBillInfoDialog.observe(viewLifecycleOwner) {
            if (it) {
                addBillInfoDialog()
                theSupplierBillInfoViewModel.addBillInfoDialogComplete()
            }
        }
        theSupplierBillInfoViewModel.navToTheSupplierBill.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    TheSupplierBillInfoFragmentDirections.actionTheSupplierBillInfoFragmentToTheSupplierBillFragment(
                        billInfoArgs.billContact,
                        theSupplierBillInfoViewModel.returnContact()
                    )
                )
                theSupplierBillInfoViewModel.navToTheSupplierBillComplete()
            }
        }
        return binding.root
    }

    private fun addBillInfoDialog() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogBuilder = dialogAlert.create()
        val view = DialogAddItemSupplierBinding.inflate(layoutInflater)
        view.itemInfo = itemInfo
        view.btnAddItemSupplier.setOnClickListener {
            theSupplierBillInfoViewModel.addBillInfo(billInfoArgs.billContact.billId, itemInfo)
            dialogBuilder.dismiss()
        }
        dialogBuilder.setView(view.root)
        dialogBuilder.show()
    }


}